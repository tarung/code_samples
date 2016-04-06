package com.dataextractor.controller;

import static com.dataextractor.util.GridUtils.pageNumber;
import static com.dataextractor.util.GridUtils.totalNumberOfPages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.dataextractor.gen.dao.ExtractionLogDao;
import com.dataextractor.gen.dao.ExtractionProfileDao;
import com.dataextractor.gen.dao.SapSystemDao;
import com.dataextractor.gen.dao.ScheduledTaskDao;
import com.dataextractor.gen.dao.spring.ExtractionLogDaoImpl;
import com.dataextractor.gen.dao.spring.ExtractionTaskDaoImpl;
import com.dataextractor.gen.dto.ExtractionTask;
import com.dataextractor.gen.dto.SapSystem;
import com.dataextractor.gen.dto.SapSystemPk;
import com.dataextractor.gen.dto.ScheduledTask;
import com.dataextractor.gen.dto.TaskStatus;
import com.dataextractor.gen.exceptions.DaoException;
import com.dataextractor.model.JqGridData;
import com.dataextractor.util.GridUtils;

/** The Task Controller. */
@Controller
@RequestMapping("/tasks")
@SuppressWarnings("all")
public class TaskController extends AbstractController {

    /** The task DAO. */
    @Autowired
    private ExtractionTaskDaoImpl taskDao;

    /** The task log DAO. */
    @Autowired
    private ExtractionLogDaoImpl logDao;


    /** The profile DAO. */
	@Autowired
	private ExtractionProfileDao profileDao;

	@Autowired
	private ScheduledTaskDao scheduledTaskDao;


    @RequestMapping(value = "getStatus", method = RequestMethod.GET)
    @ResponseBody
    public TaskStatus getStatus(@RequestParam("taskId") final Long taskId)
            throws DaoException {
    	return taskDao.getTaskProgress(taskId);
    }

    @RequestMapping(value = "loadScheduleTask", method = RequestMethod.GET)
    public ModelAndView loadScheduleTask(@RequestParam("profileId") final Long profileId,
    		@RequestParam(value="taskId", required=false) final Long taskId)
            throws DaoException {

    	ModelAndView mdl = new ModelAndView("scheduleTask");
		mdl.addObject("profile", profileDao.findByPrimaryKey(profileId));
		if(taskId != null){

			ScheduledTask stask = scheduledTaskDao.findByPrimaryKey(taskId);

			long rept = stask.getRepeatAfter();
			int days = (int)(rept/(24*60*60*1000));
			if (days>0){
				mdl.addObject("repeatAfterDays",  days);
				rept = rept % (days*24*60*60*1000);
			}
			int hours = (int)(rept/(60*60*1000));
			if(hours > 0){
				mdl.addObject("repeatAfterHours",  hours);
			}
			int mins = (int)(rept/(60*1000));
			if(mins > 0){
				mdl.addObject("repeatAfterMins",  mins);
			}
			mdl.addObject("startDate", stask.getStartDate());
			mdl.addObject("endDate", stask.getEndDate());
		}
		return mdl;
    }

	@RequestMapping(value = "getDetails", method = RequestMethod.GET)
	public ModelAndView getDetails( @RequestParam("taskId") final Long taskId,
			final HttpSession session) throws DaoException {
		ModelAndView mdl = new ModelAndView("taskDetail");
		mdl.addObject("task", taskDao.findByPrimaryKey(taskId));
		return mdl;
	}

	@RequestMapping(value = "getLog", method = RequestMethod.GET)
	public ModelAndView getLog( @RequestParam("taskId") final Long taskId,
			final HttpSession session) throws DaoException {
		ModelAndView mdl = new ModelAndView("taskLog");
		mdl.addObject("task", taskDao.findByPrimaryKey(taskId));
		mdl.addObject("taskId", taskId);
		return mdl;
	}

	private static SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");



	@RequestMapping(value = "saveScheduledTask", method = RequestMethod.POST)
	public ModelAndView saveScheduledTask(
			@RequestParam("profileName") final String profileName,
			@RequestParam("profileId") final Long profileId,
			@RequestParam("startDate") final String startDate,
			@RequestParam("endDate") final String endDate,
			@RequestParam("repeatPeriodically") final String repeatPeriodically,
			@RequestParam("repeatAfterDays") final String repeatAfterDays,
			@RequestParam("repeatAfterHours") final String repeatAfterHours,
			@RequestParam("repeatAfterMins") final String repeatAfterMins,
			final HttpSession session) throws DaoException {

		boolean repeat = repeatPeriodically != null ? Boolean
				.parseBoolean(repeatPeriodically) : false;
		Date stDte = null;
		Date eDte = null;

		try {
			stDte = dtFormat.parse(startDate);
			if (!isEmpty(endDate))
				eDte = dtFormat.parse(endDate);
			if (eDte.compareTo(stDte) <= 0) {
				return returnError("Start Date must be less than end date.",
						profileId, startDate, endDate);
			}
		} catch (Exception e) {
			return returnError("Invalid date Format!", profileId, startDate,
					endDate);
		}
		if (isEmpty(startDate)) {
			return returnError("Please select start date!", profileId,
					startDate, endDate);
		}
		if (repeat) {
			if (isEmpty(repeatAfterDays) && isEmpty(repeatAfterHours)
					&& isEmpty(repeatAfterMins)) {
				return returnError(
						"Please select values for time values for preodical tasks!",
						profileId, startDate, endDate);
			}
		}

		long repeatAfterTime = 0;

		if (repeat) {
			if (!isEmpty(repeatAfterDays))
				repeatAfterTime += Integer.parseInt(repeatAfterDays) * 24 * 60
						* 60 * 1000;
			if (!isEmpty(repeatAfterHours))
				repeatAfterTime += Integer.parseInt(repeatAfterHours) * 60 * 60 * 1000;
			if (!isEmpty(repeatAfterMins))
				repeatAfterTime += Integer.parseInt(repeatAfterMins) * 60 * 1000;
		}
		ScheduledTask task = new ScheduledTask();
		task.setProfileId(profileId);
		task.setName(profileName);
		task.setNextExecutionTime(stDte);
		task.setEndDate(eDte);
		task.setRepeatAfter(repeatAfterTime);
		task.setStartDate(stDte);
		scheduledTaskDao.insert(task);

		ModelAndView mdl = new ModelAndView("successMessage");
		mdl.addObject("message", "Task scheduled successfully.");
		return mdl;

	}

	private ModelAndView returnError(String message, Long profileId,
			String startDate, String endDate) throws DaoException {
		ModelAndView mdl = new ModelAndView("scheduleTask");
		mdl.addObject("profile", profileDao.findByPrimaryKey(profileId));
		mdl.addObject("errorMsg", message);
		mdl.addObject("startDate", startDate);
		mdl.addObject("endDate", endDate);
		return mdl;
	}

	private boolean isEmpty(final String repeatAfterDays) {
		return repeatAfterDays == null || repeatAfterDays.isEmpty();
	}

	@RequestMapping(value = "taskLog", method = RequestMethod.GET)
	public ModelAndView taskLog( @RequestParam("taskId") final Long taskId,
			final HttpSession session) throws DaoException {

		ModelAndView mdl = new ModelAndView("logPane");
		mdl.addObject("tasklog", logDao.findDynamic("task_id=" + taskId + " order by id desc"));
		return mdl;
	}

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public JqGridData<ExtractionTask> get(@RequestParam("page") final int page,
            @RequestParam("rows") final int rows,
            @RequestParam("sidx") final String sortBy,
            @RequestParam("sord") final String sortDirection)
            throws DaoException {

    	String orderby = "1=1 order by ";

    	final int res = 0;
        if (sortBy == null || sortBy.equals("") || sortBy.equals("id")) {
            orderby += "id";
        } else {
            orderby += ExtractionTask.fieldMap.get(sortBy);
        }
        orderby += " " + sortDirection;

        final List<ExtractionTask> rules = taskDao.findDynamic(orderby);
        final int totalPages = totalNumberOfPages(rules, rows);
        final int pageNumber = pageNumber(rules, page, rows);
        final int totalRecords = rules.size();
        final List<ExtractionTask> data = GridUtils.dataForPage(rules, page, rows);

        final JqGridData<ExtractionTask> gridData = new JqGridData<ExtractionTask>(
                totalPages, pageNumber, totalRecords, data);
        return gridData;
    }


    @RequestMapping(value = "scheduledTasks", method = RequestMethod.GET)
    @ResponseBody
    public JqGridData<ScheduledTask> scheduledTasks(@RequestParam("page") final int page,
            @RequestParam("rows") final int rows,
            @RequestParam("sidx") final String sortBy,
            @RequestParam("sord") final String sortDirection)
            throws DaoException {

    	String orderby = "1=1 order by ";

    	final int res = 0;
        if (sortBy == null || sortBy.equals("") || sortBy.equals("id")) {
            orderby += "id";
        } else {
            orderby += ScheduledTask.fieldMap.get(sortBy);
        }
        orderby += " " + sortDirection;

        final List<ScheduledTask> rules = scheduledTaskDao.findDynamic(orderby);
        final int totalPages = totalNumberOfPages(rules, rows);
        final int pageNumber = pageNumber(rules, page, rows);
        final int totalRecords = rules.size();
        final List<ScheduledTask> data = GridUtils.dataForPage(rules, page, rows);

        final JqGridData<ScheduledTask> gridData = new JqGridData<ScheduledTask>(
                totalPages, pageNumber, totalRecords, data);
        return gridData;
    }

}
