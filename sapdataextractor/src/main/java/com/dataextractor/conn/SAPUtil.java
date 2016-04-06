package com.dataextractor.conn;

import static com.sap.conn.jco.JCoDestinationManager.getDestination;
import static com.sap.conn.jco.ext.Environment.registerDestinationDataProvider;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.dataextractor.gen.dao.SapSystemDao;
import com.dataextractor.gen.dto.ProfileTableField;
import com.dataextractor.gen.dto.SapSystem;
import com.dataextractor.model.ProfileTableVO;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.Environment;

/** The class providing utility methods to connect with SAP. */
public class SAPUtil {

	private static final int ROW_COUNT_BATCH_SIZE = 35000;

	/** The class ResultCursorImpl. */
	private static class ResultCursorImpl implements ResultCursor {

		/** The field names. */
		private final String[] fieldNames;

		/** The field values. */
		private String[] fieldValues;

		/** The row number. */
		private int rowNum;

		/** The total rows. */
		private final int totalRows;

		/**
		 * Instantiates a new result cursor implementation.
		 *
		 * @param fieldNames the field names.
		 * @param totalRows the total rows.
		 */
		ResultCursorImpl(final String[] fieldNames, final int totalRows) {
			this.fieldNames = fieldNames;
			this.totalRows = totalRows;
		}

		/** {@inheritDoc} */
		@Override
		public String[] fieldNames() {
			return fieldNames;
		}

		/** {@inheritDoc} */
		@Override
		public String[] fieldValues() {
			return fieldValues;
		}

		/** {@inheritDoc} */
		public void fieldValues(final String[] fVals) {
			fieldValues = fVals;
		}

		/** {@inheritDoc} */
		@Override
		public int rowNum() {
			return rowNum;
		}

		/** {@inheritDoc} */
		public void rowNum(final int rNum) {
			rowNum = rNum;
		}

		/** {@inheritDoc} */
		@Override
		public int totalRows() {
			return totalRows;
		}
	}

	/** The interface Query. */
	public static interface Query {

		/**
		 * Field names.
		 *
		 * @return the Field names.
		 */
		public String[] fieldNames();

		/**
		 * Sap System.
		 *
		 * @return the sap system
		 */
		public SapSystem sapSys();

		/**
		 * Search filter.
		 *
		 * @return the Search filter.
		 */
		public String searchFilter();

		/**
		 * Table name.
		 *
		 * @return the Table name.
		 */
		public String tableName();

	}

	/** The Interface ResultCursor. */
	public static interface ResultCursor {

		/**
		 * Field names.
		 *
		 * @return the Field names.
		 */
		public String[] fieldNames();

		/**
		 * Field values.
		 *
		 * @return the Field values.
		 */
		public String[] fieldValues();

		/**
		 * Row number.
		 *
		 * @return the Current Row number.
		 */
		public int rowNum();

		/**
		 * Total rows.
		 *
		 * @return the Total rows.
		 */
		public int totalRows();
	}

	/** The Interface RowProcessor. */
	public static interface RowProcessor {

		/**
		 * Process.
		 *
		 * @param row the row
		 */
		public void process(ResultCursor row);
	}

	/** The profile DAO. */
	@Autowired
	private SapSystemDao sapDao;

	/**
	 * Gets the query.
	 *
	 * @param sapSys the SAP system.
	 * @param filter the filter.
	 * @param fieldName the field name.
	 * @param tableName the table name.
	 * @return the query.
	 */
	private Query getQuery(final SapSystem sapSys, final String fltr,
			final String[] fieldName, final String tableName) {

		return new Query() {

			@Override
			public String[] fieldNames() {
				return fieldName;
			}

			@Override
			public SapSystem sapSys() {
				return sapSys;
			}

			@Override
			public String searchFilter() {
				return fltr;
			}

			@Override
			public String tableName() {
				return tableName;
			}
		};
	}

	/**
	 * Perform query.
	 *
	 * @param query the query object.
	 * @param processor the row processor object.
	 * @return number of rows actually processed.
	 */
	public int performQuery(final Query query, final RowProcessor processor){
		return performQuery(query, processor, null, null);
	}


	public int countRecordsWithoutFilters(String destinationName, String tableName) {

		System.out.println("countRecordsWithoutFilters table " + tableName);

		try {
			final DestinationDataProvider provider = DestinationDataProvider
					.getInstance(sapDao);
			if (!Environment.isDestinationDataProviderRegistered()) {
				registerDestinationDataProvider(provider);
			}
			final JCoDestination destination = getDestination(destinationName);
			final JCoFunction function = destination.getRepository()
					.getFunction("RFC_GET_TABLE_ENTRIES");
			if (function == null) {
				throw new RuntimeException(
						"RFC_GET_TABLE_ENTRIES not found in SAP.");
			}
			function.getImportParameterList().setValue("TABLE_NAME", tableName);
			function.execute(destination);

			return Integer.parseInt(function.getExportParameterList()
					.getString("NUMBER_OF_ENTRIES"));

		} catch (final Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
    }

	/**
	 * Perform query query to find total number of records present.
	 *
	 * @param query the query object.
	 * @return number of records.
	 */
	public int countRecordsWithFilters(final Query query, final String colName){

		System.out.println("countRecordsWithFilters table " + query.tableName());
		Query countQuery = new Query() {

			@Override
			public String tableName() {
				return query.tableName();
			}
			@Override
			public String searchFilter() {
				return query.searchFilter();
			}
			@Override
			public SapSystem sapSys() {
				return query.sapSys();
			}
			@Override
			public String[] fieldNames() {
				return new String[]{colName};
			}
		};

		final DestinationDataProvider provider = DestinationDataProvider.getInstance(sapDao);
		int total = 0 ;

		try {

			if(!Environment.isDestinationDataProviderRegistered()) {
				registerDestinationDataProvider(provider);
			}
			String destinationName = query.sapSys().getDestinationName();
			final JCoDestination destination = getDestination(destinationName);

			int startFrom = 0;
			int rows = 0 ;

			do {

				rows = performInternal(destination, countQuery, null, startFrom, ROW_COUNT_BATCH_SIZE);
				startFrom += rows;
				total += rows;
				//System.out.println("count >>" + total);

			}while(rows == ROW_COUNT_BATCH_SIZE);

		} catch (JCoException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return total;
	}

	/**
	 * Perform query.
	 *
	 * @param query the query.
	 * @param processor the processor.
	 * @param startFrom the start from.
	 * @param numberOfRows the number of rows.
	 * @return the processed records.
	 */
	public int performQuery(final Query query, final RowProcessor processor,
			final Integer startFrom, final Integer numberOfRows) {
		final DestinationDataProvider provider = DestinationDataProvider.getInstance(sapDao);
		try {
			if(!Environment.isDestinationDataProviderRegistered()){
				registerDestinationDataProvider(provider);
			}
			String destinationName = query.sapSys().getDestinationName();
			final JCoDestination destination = getDestination(destinationName);
			return performInternal(destination, query, processor, startFrom, numberOfRows);
		} catch (JCoException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Actually performs the query.
	 *
	 * @param destination SAP destination.
	 * @param query  the query object.
	 * @param processor pass null if we only need row count.
	 * @param startFrom start from row number
	 * @param numberOfRows number of rows.
	 *
	 * @return
	 */
	private int performInternal(JCoDestination destination, final Query query,
			final RowProcessor processor, final Integer startFrom,
			final Integer numberOfRows) throws JCoException {


		final JCoRepository repository = destination.getRepository();
		final JCoFunction function = repository.getFunction("RFC_READ_TABLE");

		if (function == null) {
			throw new RuntimeException("BAPI RFC_READ_TABLE not found in SAP.");
		}

		final JCoParameterList impParamList = function.getImportParameterList();
		impParamList.setValue("QUERY_TABLE", query.tableName());
		impParamList.setValue("DELIMITER", "~");

		if(startFrom != null && startFrom >0){
			impParamList.setValue("ROWSKIPS", startFrom);
		}
		if(numberOfRows != null && numberOfRows >0){
			impParamList.setValue("ROWCOUNT", numberOfRows);
		}

		final JCoParameterList tblParamLst = function.getTableParameterList();
		final JCoTable table = tblParamLst.getTable("FIELDS");

		if (query.fieldNames() != null) {
			for (final String fName : query.fieldNames()) {
				table.appendRow();
				table.setValue("FIELDNAME", fName);
				//System.out.println("FIELDNAME >> " + fName);
			}
		}
		if (query.searchFilter() != null && !query.searchFilter().isEmpty()) {
			final JCoTable options = function.getTableParameterList().getTable("OPTIONS");
			options.appendRow();
			System.out.println("filter >> " + query.searchFilter());
			options.setValue("TEXT", query.searchFilter());
		}

		try {
			function.execute(destination);
		} catch (final AbapException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		int rowsProcesssed = 0;

		if(processor != null){

			final JCoTable codes = tblParamLst.getTable("FIELDS");
			final int fieldCount = codes.getNumRows();
			final String[] fields = new String[fieldCount];

			for (int i = 0; i < fieldCount; i++) {
				codes.setRow(i);
				fields[i] = codes.getString("FIELDNAME");
			}

			final JCoTable rows = tblParamLst.getTable("DATA");
			final int numRows = rows.getNumRows();
			final ResultCursorImpl cur = new ResultCursorImpl(fields, numRows);
			final String[] fldValues = new String[fieldCount];

			for (int i = 0; i < numRows; i++) {

				rows.setRow(i);
				String result = rows.getString("WA");
				String[] vals =  result.split("~");
				System.arraycopy(vals, 0, fldValues, 0, vals.length);

				if(result.endsWith("~")){
					vals[vals.length-1] = "";
				}
				cur.fieldValues(fldValues);
				cur.rowNum(i + 1);
				processor.process(cur);
				rowsProcesssed++;
			}
		}else{
			final JCoTable rows = tblParamLst.getTable("DATA");
			rowsProcesssed = rows.getNumRows();
		}
		return rowsProcesssed;
	}

	public String testConnection(final SapSystem sapSys) {
		DestinationDataProvider provider = DestinationDataProvider.getInstance(sapDao);
		String destinationName = sapSys.getDestinationName();
		try {
			provider.addTempDestination(sapSys);
			if(!Environment.isDestinationDataProviderRegistered()){
				registerDestinationDataProvider(provider);
			}
			JCoDestination destination = getDestination(destinationName);
			destination.ping();
	        System.out.println("Attributes:");
	        System.out.println(destination.getAttributes());
	        System.out.println();
			return null;
		} catch (JCoException e) {
			return e.getMessage();
		}finally {
			provider.removeTempDestination(destinationName);
		}
	}

	/**
	 * Search table in SAP system by name, initializes, Table description, and
	 * Table Fields as well.
	 *
	 * @param tableName the table name.
	 * @param sapSys the SAP System DTO.
	 * @return the profile table Value object, null if table not found.
	 */
	public ProfileTableVO searchTableByName(final String tableName, final SapSystem sapSys) {

		final ProfileTableVO vo = new ProfileTableVO();
		String fltr = "TABNAME = '" + tableName + "'";

		// query to get the table info from "DD02L"
		Query qry = getQuery(sapSys, fltr, new String[] { "TABNAME" }, "DD02L");
		final RowProcessor processor = new RowProcessor() {
			@Override
			public void process(final ResultCursor row) {
				vo.setTableName(row.fieldValues()[0].trim());
			}
		};
		if (performQuery(qry, processor, 0, 1) < 1) {
			// if no rows processed it means table doesn't exist.
			return null;
		}

		// TODO E is hard coded.
		fltr = "TABNAME = '" + tableName + "' AND DDLANGUAGE = '" + sapSys.getLanguageCode() + "'";

		// query to get the table description from "DD02T"
		qry = getQuery(sapSys, fltr, new String[] { "DDTEXT" }, "DD02T");
		performQuery(qry, new RowProcessor() {
			@Override
			public void process(final ResultCursor row) {
				vo.setDescription(row.fieldValues()[0].trim());
			}
		}, 0, 1);

		fltr = "TABNAME = '" + tableName + "'";

		// query to get the table description from "DD02T"
		qry = getQuery(sapSys, fltr, new String[] { "FIELDNAME", "DATATYPE", "LENG", "DECIMALS", "POSITION" }, "DD03L");
		performQuery(qry, new RowProcessor() {

			@Override
			public void process(final ResultCursor row) {
				final String[] vals = row.fieldValues();

				// If field type is null or not supported don't add it.
				if (vals[1].trim().isEmpty()) {
					return;
				}
				try {
					SAPDataType.valueOf(vals[1].trim());
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}

				Set<ProfileTableField> fields = vo.getFields();
				if (fields == null) {
					fields = new TreeSet<ProfileTableField>( new Comparator<ProfileTableField>() {
						@Override
						public int compare(ProfileTableField o1, ProfileTableField o2) {
							return (new Integer(o1.getPosition())).compareTo(o2.getPosition());
						}
					});
					vo.setFields(fields);
				}

				final ProfileTableField fld = new ProfileTableField();
				fld.setFieldName(vals[0].trim());
				fld.setFieldType(vals[1].trim());
				fld.setFieldLength(Integer.valueOf(vals[2].trim()));
				fld.setDecimals(Integer.valueOf(vals[3].trim()));
				fld.setPosition(Integer.valueOf(vals[4].trim()));
				fields.add(fld);
			}
		});
		return vo;
	}
}
