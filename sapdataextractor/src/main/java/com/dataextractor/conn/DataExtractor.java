package com.dataextractor.conn;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import com.dataextractor.conn.SAPUtil.ResultCursor;
import com.dataextractor.gen.dao.DbConnectionDao;
import com.dataextractor.gen.dao.ExtractionLogDao;
import com.dataextractor.gen.dao.ExtractionTaskDao;
import com.dataextractor.gen.dao.SapSystemDao;
import com.dataextractor.gen.dao.spring.ExtractionLogDaoImpl;
import com.dataextractor.gen.dto.DbConnection;
import com.dataextractor.gen.dto.ExtractionLogRecord;
import com.dataextractor.gen.dto.ExtractionTask;
import com.dataextractor.gen.dto.ExtractionTaskPk;
import com.dataextractor.gen.dto.ProfileTableField;
import com.dataextractor.gen.dto.ProfileTableFieldFilter;
import com.dataextractor.gen.dto.SapSystem;
import com.dataextractor.gen.exceptions.DaoException;
import com.dataextractor.model.ExtractionProfileVO;
import com.dataextractor.model.ProfileTableVO;

/** The Data extractor class performs asynchronous data extraction. */
public class DataExtractor {

	/** The Data Extractor Task. */
	public static class ExtractorTask implements Runnable {

		/** The logger. */
		private static Logger logger = Logger.getLogger(ExtractorTask.class);

		/** The Profile value Object. */
		private ExtractionProfileVO profile;

		/** The Connection Object. */
		private Connection conn;

		/** The SAP utility Object. */
		private SAPUtil sapUtil;

		/** The SAP System DTO. */
		private SapSystem sapsys;

		/** The task DAO. */
		private ExtractionTaskDao taskDao;

		/** The Extraction logs DAO. */
		private ExtractionLogDao logDao;

		/** The scheduler id. */
		private Long schedulerId;

		/**
		 * Instantiates a new extractor task.
		 *
		 * @param profile the profile.
		 * @param conn the connection object.
		 * @param sapUtil the sap utility class.
		 * @param sapsys the SAP system DTO.
		 */
		public ExtractorTask(ExtractionProfileVO profile, Long schedulerId,
				Connection conn, SAPUtil sapUtil, SapSystem sapsys,
				ExtractionTaskDao taskDao, ExtractionLogDao logDao) {

			this.profile = profile;
			this.conn = conn;
			this.sapUtil = sapUtil;
			this.sapsys = sapsys;
			this.taskDao = taskDao;
			this.logDao = logDao;
			this.schedulerId = schedulerId;
		}



		private String getSmallestColmn(ProfileTableVO table) {

			String colName = "";
			int minsize = -1;
			Set<ProfileTableField> cols = table.getFields();

			if (cols == null || cols.isEmpty()) {
				// TODO throw exception here.
			}
			for (ProfileTableField profileTableField : cols) {
				int fieldLength = profileTableField.getFieldLength();
				if (minsize == -1 || minsize > fieldLength) {
					minsize = fieldLength;
					colName = profileTableField.getFieldName();
				}
			}
			return colName;
		}


		private boolean checkTable(String tableName, Connection connection) throws SQLException{
			DatabaseMetaData metadata = connection.getMetaData();
			ResultSet resultSet;
			resultSet = metadata.getTables(null, null, tableName, null);
			if(resultSet.next())
				return true;
			return false;
		}

		/** Run Method. */
		public void run() {

			List<ProfileTableVO> tables = profile.getTables();
			final SapSystem system = sapsys;
			int batchsize = profile.getWriteBatchSize();

			Date startTime = new Date();
			ExtractionTask task = createTaskDTO(startTime);
			logMessage("Starting Extraction for " + profile.getProfileName(), task, null);

			int totalCount = 0;
			int percent = 0;

			//Check tables before counting rows, becuase counting rows is slower.
			if("ABORT".equals(profile.getIfTableExists())){
				for (final ProfileTableVO table : tables) {
					String tableName = profile.getProfileName() + "_" + table.getTableName();
					try {
						boolean exists = checkTable(tableName, conn);
						if(exists){
							String message = "Aborting the extraction since table :" + tableName
							+ " already exists and condition is set to 'ABORT'" ;
							terminateTask(task, "ABORTED", 0, message, table.getTableName(), true);
							return ;
						}
					} catch (Exception e) {
						terminateTask(task, "ABORTED", 0, e.getMessage(), table.getTableName(), true);
						return;
					}
				}
			}

			try {

				totalCount = countRows(tables, system, task, totalCount);
				if(totalCount == 0){
					terminateTask(task, "COMPLETED", 0, "No records expected to be extracted! ", null, false);
					return;
				}

				int totalRecordsExtracted = 0;
				boolean errorOccurred = false;

				for (final ProfileTableVO table : tables) {

					try {

						logMessage("Start processing table : " + table.getTableName(), task, table.getTableName());
						String tableName = profile.getProfileName() + "_" + table.getTableName();

						String[] fieldNames = getFieldNames(table);
						boolean moreRows = false;
						boolean exists = checkTable(tableName, conn);

						if(exists){
							String ifTableExists = profile.getIfTableExists();
							if(ifTableExists.equals("DROP")){
								dropTable(table, task);
								createTable(table, task);
							}
							else if(ifTableExists.equals("APPEND")){
								logMessage("Table :" + tableName + " already exists ", task, table.getTableName());
							}
							else {
								// this means "SKIP" this table and continue.
								logErrorMessage("Skipping table :" + tableName + " as it already exists!", task, table.getTableName());
								continue;
							}
						}
						else {
							createTable(table, task);
						}
						SAPUtil.Query qry = createQuery(system, table, fieldNames);
						BatchProcessor rowProcessor = new BatchProcessor(table, profile, conn);
						int tableRowCount = 0;

						do {

							int processed = sapUtil.performQuery(qry, rowProcessor, tableRowCount, batchsize);
							totalRecordsExtracted += processed;
							rowProcessor.commit();
							moreRows = (processed == batchsize);
							tableRowCount = tableRowCount + processed;
							percent = (totalRecordsExtracted * 100 / totalCount);

							// Update DB with percent completion.
							if (task.getPercentCompletion() != percent) {
								// TODO if 100% avoid this call since second call
								// below will set to 100%
								task.setPercentCompletion(percent);
								taskDao.update(task.createPk(), task);
							}

							logMessage("Total records extracted for table : " + tableName + " = " + tableRowCount, task, table.getTableName());
							logMessage("Overall Progress : " + totalRecordsExtracted + " of " + totalCount + " (" + percent + "%)" , task, null);

						} while (moreRows);

						logMessage( "Completed processing table : " + table.getTableName(), task, table.getTableName());
					}catch(Exception e){
						if(!profile.isContinueOnFailure()){
							throw e;
						}
						e.printStackTrace();
						logErrorMessage(e.getMessage(), task, table.getTableName());
						errorOccurred = true;
					}
				}
				// update DB with percent completion.
				task.setPercentCompletion(percent);
				String finalStatus ;
				if(!errorOccurred) {
					finalStatus = "COMPLETED";
				}
				else {
					finalStatus = "COMPLETE_WITH_ERRORS";
				}
				terminateTask(task, finalStatus, percent, "Completed Extraction for " + profile.getProfileName(), null, false);

			} catch (Throwable e) {
				e.printStackTrace();
				String message = "Aborted Extraction for " + profile.getProfileName() + " error:" + e.getMessage();
				terminateTask(task, "ABORTED", percent, message, null, true);
			}
		}

		private int countRows(List<ProfileTableVO> tables,
				final SapSystem system, ExtractionTask task, int totalCount) {

			for (final ProfileTableVO table : tables) {

				int recordCount = 0;
				//if(table.getFilters() != null && table.getFilters().size() >0){
				String[] fieldNames = getFieldNames(table);
				SAPUtil.Query qry = createQuery(system, table, fieldNames);
				recordCount = sapUtil.countRecordsWithFilters(qry,
						getSmallestColmn(table));
				/*}
				else {
					recordCount = sapUtil.countRecordsWithoutFilters(system.getDestinationName(), table.getTableName());
				}*/
				totalCount += recordCount;
				logMessage("Total number of records expected to be extracted: "
						+ recordCount + " for table :" + table.getTableName(),
						task, table.getTableName());
			}
			return totalCount;
		}

		private void logMessage(String msg, ExtractionTask task,
				String tableName) {

			Date startTime = new Date(System.currentTimeMillis());
			ExtractionLogRecord logR = new ExtractionLogRecord();
			logR.setTaskId(task.getId());
			logR.setMessage(msg);
			logR.setTimeStamp(startTime);
			if (tableName != null) {
				logR.setTableName(tableName);
			}
			logDao.insert(logR);
		}

		private void logErrorMessage(String msg,
				ExtractionTask task, String tableName) {

			Date startTime = new Date(System.currentTimeMillis());
			ExtractionLogRecord logR = new ExtractionLogRecord();
			logR.setTaskId(task.getId());
			logR.setMessage(msg);
			logR.setTimeStamp(new Date(startTime.getTime()));
			logR.setError(true);
			if (tableName != null) {
				logR.setTableName(tableName);
			}
			logDao.insert(logR);
		}

		private ExtractionTask createTaskDTO(Date now) {

			ExtractionTask task = new ExtractionTask();
			task.setProfileName(profile.getProfileName());
			task.setDescription(profile.toString());
			task.setStatus("INPROGRESS");
			task.setStartedOn(now);
			task.setPercentCompletion(0);
			if(this.schedulerId != null)
				task.setSchedulerId(this.schedulerId);
			ExtractionTaskPk pk = taskDao.insert(task);
			task.setId(pk.getId());
			currentTasks.add(""+pk.getId());
			return task;

		}

		private void terminateTask(ExtractionTask task, String status, int percentage, String logMessage, String tableName, boolean isError) {

			if(logMessage != null){
				if(isError)
					logErrorMessage(logMessage, task, tableName);
				else
					logMessage(logMessage, task, tableName);
			}
			task.setStatus(status);
			task.setCompletedOn(new Date(System.currentTimeMillis()));
			task.setPercentCompletion(percentage);
			currentTasks.remove(""+ task.getId());
			try {
				taskDao.update(task.createPk(), task);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}


		private String[] getFieldNames(final ProfileTableVO table) {
			Set<ProfileTableField> fields = table.getFields();
			String[] names = new String[fields.size()];
			int count = 0;
			for(ProfileTableField fld : fields){
				names[count++] = fld.getFieldName();
			}
			return names;

		}

		private static class BatchProcessor implements SAPUtil.RowProcessor {

			ProfileTableVO table;
			ExtractionProfileVO profile;
			Connection conn;
			PreparedStatement pstmt;
			Map<String, ProfileTableField> fieldNameMap;

			public BatchProcessor(ProfileTableVO table,
					ExtractionProfileVO profile, Connection conn) {

				this.table = table;
				this.profile = profile;
				this.conn = conn;

				fieldNameMap = new HashMap<String, ProfileTableField>();
				Set<ProfileTableField> fields = table.getFields();

				for (ProfileTableField profileTableField : fields) {
					fieldNameMap.put(profileTableField.getFieldName(), profileTableField);
				}
			}

			@Override
			public void process(ResultCursor row) {

				try {

					String[] fieldNames2 = row.fieldNames();

					if (pstmt == null) {

						String tableName = profile.getProfileName() + "_" + table.getTableName();
						String SQL = "INSERT INTO " + tableName + " (";
						String valuesStr = "";

						int count = 0;

						for (String fName : fieldNames2) {
							String comma = count < (fieldNames2.length - 1) ? "," : "";
							SQL += fName + comma;
							valuesStr += "?" + comma;
							count++;
						}
						SQL = SQL + ") VALUES (" + valuesStr + ")";
						pstmt = conn.prepareStatement(SQL);
					}

					String[] values = row.fieldValues();
					int fcount = 1;

					for (String value : values) {

						String fName = fieldNames2[fcount - 1];
						ProfileTableField fld = fieldNameMap.get(fName);
						SAPDataType type = SAPDataType.valueOf(fld
								.getFieldType());

						value = value != null ? value.trim() : null;
						type.updateSQLStatement(pstmt, fcount++, value);

					}
					pstmt.addBatch();

				} catch (SQLException e) {
					logger.error(e);
				}
			}

			void commit() throws SQLException {
				if (pstmt != null) {
					pstmt.executeBatch();
					if (!conn.getAutoCommit()) {
						conn.commit();
					}
					pstmt.close();
					pstmt = null;
				}
			}
		}

		/**
		 * Creates the query.
		 *
		 * @param system the system.
		 * @param table the table.
		 * @param fieldNames the field names.
		 * @return the SAP utility query.
		 */
		private SAPUtil.Query createQuery(final SapSystem system,
				final ProfileTableVO table, final String[] fieldNames) {

			SAPUtil.Query qry = new SAPUtil.Query() {

				@Override
				public String tableName() {
					return table.getTableName();
				}

				@Override
				public String searchFilter() {
					String filters = "";
					List<ProfileTableFieldFilter> filterslist = table
							.getFilters();
					if (filterslist != null && !filterslist.isEmpty()) {
						for (ProfileTableFieldFilter f : filterslist) {
							filters += f.getFilterString();
						}
					}
					return filters;
				}

				@Override
				public SapSystem sapSys() {
					return system;
				}

				@Override
				public String[] fieldNames() {
					return fieldNames;
				}
			};
			return qry;
		}

		/**
		 * Creates the table.
		 *
		 * @param table the table.
		 * @throws SQLException the SQL exception.
		 * @throws DaoException the DAO exception.
		 */
		private void dropTable(ProfileTableVO table, ExtractionTask task) throws SQLException,
				DaoException {

			String tableName = profile.getProfileName() + "_" + table.getTableName();
			String createTable = " DROP TABLE " + tableName + " ;" ;
			Statement statement = conn.createStatement();
			statement.execute(createTable);
			logMessage("Dropped Table using SQL : " + createTable, task, table.getTableName());
			statement.close();
		}

		private void createTable(ProfileTableVO table, ExtractionTask task)
		throws SQLException, DaoException {

			String tableName = profile.getProfileName() + "_" + table.getTableName();
			int count = 1;
			String createTable = " CREATE TABLE " + tableName + " (";
			Set<ProfileTableField> fields = table.getFields();

			for (ProfileTableField f : fields) {

				SAPDataType dataType = SAPDataType.valueOf(f.getFieldType());
				String comma = count < fields.size() ? "," : "";
				createTable += f.getFieldName() + " " + dataType.toSQLColumn(f) + comma + " \n";
				count++;
			}
			createTable += ")";

			Statement statement = conn.createStatement();
			statement.execute(createTable);
			logMessage("Created Table using SQL : " + createTable, task, table.getTableName());

			statement.close();
		}

	}

	/** The Task executor. */
	@Autowired
	private TaskExecutor taskExecutor;

	/** The Extraction logs DAO. */
	@Autowired
	private ExtractionLogDaoImpl logDao;

	/** The DB DAO. */
	@Autowired
	private DbConnectionDao dbDao;

	/** The DB DAO. */
	@Autowired
	private SapSystemDao sapDao;

	/** The SAP utility class. */
	@Autowired
	private SAPUtil sapUtil;

	/** The task DAO. */
	@Autowired
	private ExtractionTaskDao taskDao;

	/** The current tasks. */
	private static List<String> currentTasks = new ArrayList<String>();

	/**
	 * Perform extraction.
	 *
	 * @param profile the profile.
	 * @throws DaoException the DAO exception.
	 * @throws SQLException the SQL exception.
	 */
	public void performExtraction(ExtractionProfileVO profile, Long schedulerId)
			throws DaoException {

		DbConnection dbConnectionDTO = dbDao.findByPrimaryKey(profile.getDbConnectionId());
		Connection conn = getConnection(dbConnectionDTO);
		SapSystem sapsys = sapDao.findByPrimaryKey(profile.getSapSystemId());
		taskExecutor.execute(new ExtractorTask(profile, schedulerId, conn, sapUtil, sapsys, taskDao, logDao));
	}

	public static boolean checkIfRunning(Long taskId){
		return currentTasks.contains(""+taskId);
	}
	/**
	 * Gets the connection.
	 *
	 * @param connectionDTO the connection DTO.
	 * @return the connection.
	 * @throws DaoException the DAO exception.
	 */
	public Connection getConnection(DbConnection connectionDTO)
			throws DaoException {

		try {
			Class.forName(connectionDTO.getDriverClassName());
			Connection conn = DriverManager.getConnection(
					connectionDTO.getUrl(), connectionDTO.getUserName(),
					connectionDTO.getPassword());
			return conn;
		} catch (ClassNotFoundException e) {
			throw new DaoException(e.getMessage(), e);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
	}

}
