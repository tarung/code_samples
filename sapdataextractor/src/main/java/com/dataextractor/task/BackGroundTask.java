package com.dataextractor.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dataextractor.conn.DataExtractor;
import com.dataextractor.gen.dao.ExtractionLogDao;
import com.dataextractor.gen.dao.ExtractionProfileDao;
import com.dataextractor.gen.dao.ExtractionTaskDao;
import com.dataextractor.gen.dao.ProfileTableDao;
import com.dataextractor.gen.dao.ProfileTableFieldDao;
import com.dataextractor.gen.dao.ProfileTableFieldFilterDao;
import com.dataextractor.gen.dao.ScheduledTaskDao;
import com.dataextractor.gen.dto.ExtractionLogRecord;
import com.dataextractor.gen.dto.ExtractionTask;
import com.dataextractor.gen.dto.ScheduledTask;
import com.dataextractor.gen.dto.ScheduledTaskPk;
import com.dataextractor.gen.exceptions.DaoException;
import com.dataextractor.util.StaticUtil;

@Service
public class BackGroundTask {

	@Autowired
	private ScheduledTaskDao scheduledTaskDao;

	@Autowired
	private DataExtractor dataExtractor;

	@Autowired
	private ExtractionProfileDao profileDao;

	/** The table DAO. */
	@Autowired
	private ProfileTableDao tableDao;

	/** The field DAO. */
	@Autowired
	private ProfileTableFieldDao fieldDao;

	/** The filter DAO. */
	@Autowired
	private ProfileTableFieldFilterDao filterDao;

	@Autowired
	private ExtractionTaskDao taskDao;


	/** The Extraction logs DAO. */
	@Autowired
	private ExtractionLogDao logDao;


	@Scheduled(fixedDelay = 10000)
    public void checkTasks() {
		processScheduledTasks();
		updateNonRunningTasks();
    }


	private void updateNonRunningTasks() {
		try {
			List<ExtractionTask> runningtasks = taskDao.findDynamic("status = 'INPROGRESS'");

			for (ExtractionTask extractionTask : runningtasks) {
				boolean running = DataExtractor.checkIfRunning(extractionTask.getId());
				if(!running){
					Date endDate ;
					List<ExtractionLogRecord> logs = logDao.findDynamic("task_id = " + extractionTask.getId() + " order by log_time desc");
					if(logs != null && logs.size() > 0){
						endDate = logs.get(0).getTimeStamp();
					}
					else{
						endDate = new Date();
					}
					extractionTask.setCompletedOn(endDate);
					extractionTask.setStatus("ABORTED");

					taskDao.update(extractionTask.createPk(), extractionTask);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void processScheduledTasks() {
		try {

			List<ScheduledTask> tasks = scheduledTaskDao.findAll();
			Date currentDate = new Date();

			for(ScheduledTask tsk : tasks){

				Date nextExecutionTime = tsk.getNextExecutionTime();
				Date endDate = tsk.getEndDate();
				try{
					if(nextExecutionTime.compareTo(currentDate) <=0 ){
						if(tsk.getRepeatAfter() >0){
							if(endDate == null || endDate.compareTo(currentDate) > 0){
								startExtraction(currentDate, tsk);
							}
						}
						else{
							if(tsk.getLastExecutionTime() == null){
								startExtraction(currentDate, tsk);
							}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void startExtraction(Date currentDate, ScheduledTask tsk)
			throws DaoException {

		dataExtractor.performExtraction(StaticUtil.loadProfileFromDB(tsk.getProfileId(),
				profileDao, tableDao, fieldDao, filterDao), tsk.getId());
		Date nextDate = new Date(currentDate.getTime() + tsk.getRepeatAfter());
		while(nextDate.compareTo(currentDate)<= 0){
			nextDate = new Date(nextDate.getTime() + tsk.getRepeatAfter());
		}
		tsk.setLastExecutionTime(currentDate);
		tsk.setNextExecutionTime(nextDate);
		scheduledTaskDao.update(new ScheduledTaskPk(tsk.getId()), tsk);
	}
}
