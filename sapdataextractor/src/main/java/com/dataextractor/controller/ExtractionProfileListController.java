package com.dataextractor.controller;

import static com.dataextractor.util.GridUtils.dataForPage;
import static com.dataextractor.util.GridUtils.pageNumber;
import static com.dataextractor.util.GridUtils.totalNumberOfPages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dataextractor.conn.DataExtractor;
import com.dataextractor.conn.SAPUtil;
import com.dataextractor.gen.dao.DbConnectionDao;
import com.dataextractor.gen.dao.ExtractionProfileDao;
import com.dataextractor.gen.dao.ProfileTableDao;
import com.dataextractor.gen.dao.ProfileTableFieldDao;
import com.dataextractor.gen.dao.ProfileTableFieldFilterDao;
import com.dataextractor.gen.dao.SapSystemDao;
import com.dataextractor.gen.dto.DbConnection;
import com.dataextractor.gen.dto.ExtractionProfile;
import com.dataextractor.gen.dto.ExtractionProfilePk;
import com.dataextractor.gen.dto.ProfileTable;
import com.dataextractor.gen.dto.ProfileTableField;
import com.dataextractor.gen.dto.ProfileTableFieldFilter;
import com.dataextractor.gen.dto.ProfileTableFieldFilterPk;
import com.dataextractor.gen.dto.ProfileTableFieldPk;
import com.dataextractor.gen.dto.ProfileTablePk;
import com.dataextractor.gen.dto.SapSystem;
import com.dataextractor.gen.exceptions.DaoException;
import com.dataextractor.gen.exceptions.DataValidationException;
import com.dataextractor.gen.exceptions.SessionExpiredException;
import com.dataextractor.model.ExtractionProfileVO;
import com.dataextractor.model.JqGridData;
import com.dataextractor.model.ProfileTableVO;
import com.dataextractor.util.GridUtils;
import com.sap.conn.jco.JCoException;

/** The Extraction Profile List Controller. */
@Controller
@RequestMapping("/profilelist")
@SuppressWarnings("all")
public class ExtractionProfileListController extends AbstractController {

	/** The profile DAO. */
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

	/** The DB connection DAO. */
	@Autowired
	private DbConnectionDao dbSystemDao;

	/** The SAP system DAO. */
	@Autowired
	private SapSystemDao sapSystemDao;

	/** The data extractor. */
	@Autowired
	private DataExtractor dataExtractor;

	@Autowired
	private SAPUtil sapUtil;

	/** Creates the Grid data. */
	private JqGridData<ProfileTableField> createFldGridData(final int pg,
			final int rws, final List<ProfileTableField> v) {

		final int totalPages = totalNumberOfPages(v, rws);
		final int pageNumber = pageNumber(v, pg, rws);
		final int totalRecords = v.size();

		final List<ProfileTableField> data = dataForPage(v, pg, rws);
		final JqGridData<ProfileTableField> gridData = new JqGridData<ProfileTableField>(
				totalPages, pageNumber, totalRecords, data);
		return gridData;
	}

	/** Creates the filter Grid data. */
	private JqGridData<ProfileTableFieldFilter> createFltrGridData(
			final int pg, final int rws, final List<ProfileTableFieldFilter> v) {

		final int totalPages = totalNumberOfPages(v, rws);
		final int pageNumber = pageNumber(v, pg, rws);
		final int totalRecords = v.size();

		final List<ProfileTableFieldFilter> data = dataForPage(v, pg, rws);
		final JqGridData<ProfileTableFieldFilter> gridData = new JqGridData<ProfileTableFieldFilter>(
				totalPages, pageNumber, totalRecords, data);
		return gridData;
	}

	/** Creates the Grid data. */
	private JqGridData<ProfileTableVO> createGridData(final int pg,
			final int rws, final List<ProfileTableVO> v) {

		final int totalPages = totalNumberOfPages(v, rws);
		final int pageNumber = pageNumber(v, pg, rws);
		final int totalRecords = v.size();

		final List<ProfileTableVO> data = dataForPage(v, pg, rws);
		final JqGridData<ProfileTableVO> gridData = new JqGridData<ProfileTableVO>(
				totalPages, pageNumber, totalRecords, data);
		return gridData;
	}

	/**
	 * Gets the sap connection object that matched a particular ID, from a list of objects.
	 *
	 * @param sapSystemId the sap system id to be matched.
	 * @param sapconnList the list of objects.
	 * @return the result or null.
	 */
	private SapSystem getSapConn(final Long sapSystemId,
			final List<SapSystem> sapconnList) {

		SapSystem conn = null;
		for (final SapSystem sapSystem : sapconnList) {
			if (sapSystem.getId().equals(sapSystemId)) {
				conn = sapSystem;
				break;
			}
		}
		return conn;
	}

	private ProfileTableVO getTableFromSAP(final String tableName,
			final SapSystem sapConnection) {
		return sapUtil.searchTableByName(tableName, sapConnection);
	}

	/**
	 * Gets the table profile.
	 *
	 * @param whereC the where clause.
	 * @return the table profile.
	 */
	private List<ExtractionProfileVO> getTableProfileVOs(final String whereC)
			throws DaoException {

		final List<ExtractionProfile> profiles = profileDao.findDynamic(whereC);
		final List<ExtractionProfileVO> vos = new ArrayList<ExtractionProfileVO>();

		for (final ExtractionProfile extractionProfile : profiles) {

			final ExtractionProfileVO extractionProfileVO = new ExtractionProfileVO(extractionProfile);
			String whereClause = "profile_id=" + extractionProfileVO.getId();

			final List<ProfileTable> s = tableDao.findDynamic(whereClause);
			final List<ProfileTableVO> v = new ArrayList<ProfileTableVO>();

			SapSystem sapSys = sapSystemDao.findByPrimaryKey(extractionProfile.getSapSystemId());
			DbConnection dbConn = dbSystemDao.findByPrimaryKey(extractionProfile.getDbConnectionId());

			extractionProfileVO.setDbConnectionName(dbConn.getName());
			extractionProfileVO.setSapSystemName(sapSys.getDestinationName());

			for (final ProfileTable profileTable : s) {
				final ProfileTableVO tvo = new ProfileTableVO(profileTable);
				v.add(tvo);
			}
			extractionProfileVO.setTables(v);
			vos.add(extractionProfileVO);
		}
		return vos;
	}

	private ExtractionProfileVO loadProfileFromDB(final Long id)
			throws DaoException {

		final ExtractionProfileVO currentProfile;
		final ExtractionProfile profileDTO = profileDao.findByPrimaryKey(new ExtractionProfilePk(id));

		currentProfile = new ExtractionProfileVO(profileDTO);
		final List<ProfileTable> tables = tableDao.findDynamic("profile_id="+ id);

		if (tables != null && tables.size() > 0) {

			final List<ProfileTableVO> tablevos = new ArrayList<ProfileTableVO>(tables.size());

			for (final ProfileTable t : tables) {

				final ProfileTableVO profileTableVO = new ProfileTableVO(t);
				final List<ProfileTableField> flds = fieldDao.findDynamic("table_id=" + t.getId()
						+ " order by position asc");
				final List<ProfileTableFieldFilter> fltrs = new ArrayList<ProfileTableFieldFilter>();
				if (flds.size() > 0) {
					for (final ProfileTableField fld : flds) {
						fltrs.addAll(filterDao.findDynamic("field_id="+ fld.getId() + " order by id"));
					}
				}
				Set fieldSet = new TreeSet(new Comparator<ProfileTableField>(){
					@Override
					public int compare(ProfileTableField o1,
							ProfileTableField o2) {
						return new Integer(o1.getPosition()).compareTo(o2.getPosition());
					}
				});

				fieldSet.addAll(flds);
				profileTableVO.setFields(fieldSet);
				profileTableVO.setFilters(fltrs);
				tablevos.add(profileTableVO);
				filterDao.findDynamic("");
			}
			currentProfile.setTables(tablevos);
		}
		return currentProfile;
	}

	/** Persist the profile. */
	private void persist(final ExtractionProfileVO profile) {

		final ExtractionProfilePk pk = profileDao.insert(profile);
		profile.setId(pk.getId());
	}

	/** Persist the filter. */
	private void persist(final ProfileTableFieldFilter fltr) {
		fltr.setId(filterDao.insert(fltr).getId());
	}

	/** Persist the table. */
	private void persist(final ProfileTableVO table) {

		final Long id = tableDao.insert(table).getId();
		table.setId(id);
		for (final ProfileTableField fld : table.getFields()) {
			fld.setTableId(table.getId());
			fld.setId(fieldDao.insert(fld).getId());
		}
	}

	private void setTableId(final ModelAndView mdl,
			final ExtractionProfileVO currentProfile) {

		Long tid = -1l;
		if (currentProfile.getTables() != null
				&& currentProfile.getTables().size() > 0) {
			tid = currentProfile.getTables().get(0).getId();
		}
		mdl.addObject("firstTableId", tid);
	}

	private ProfileTableVO getTablebyName(final String tableName,
			final ExtractionProfileVO currentProfile) {
		ProfileTableVO table = null;
		for (final ProfileTableVO t : currentProfile.getTables()) {
			if (t.getTableName().equals(tableName)) {
				table = t;
				break;
			}
		}
		return table;
	}

	/**
	 * Adds the filter.
	 *
	 * @param tableName the table name.
	 * @param filterColumn the filter column.
	 * @param filterOperator the filter operator.
	 * @param filterCriteria the filter criteria.
	 * @param filterCriteria2 the filter criteria2.
	 * @param joinBy the join by.
	 * @param session the session.
	 *
	 * @return the model and view.
	 */
	@RequestMapping(value = "addFilter", method = RequestMethod.POST)
	public ModelAndView addFilter(
			@RequestParam("tableName") final String tableName,
			@RequestParam("tableId") final Long tableId,
			@RequestParam("filterColumn") final String filterColumn,
			@RequestParam("filterOperator") final String filterOperator,
			@RequestParam("filterCriteria") final String filterCriteria,
			@RequestParam(value = "joinBy", required = false) final String joinBy,
			@RequestParam(value = "filterCriteria2", required = false) final String filterCriteria2,
			final HttpSession session) throws DaoException, DataValidationException,
			SessionExpiredException {

		final ExtractionProfileVO currentProfile = getCurrentProfile(session);
		final SapSystem conn = sapSystemDao.findByPrimaryKey(currentProfile.getSapSystemId());
		final ProfileTableVO originalTableObject = getTableFromSAP(tableName, conn);

		if(currentProfile == null){
			throw new SessionExpiredException("Session Expired! Please start again.");
		}

		List<ProfileTableVO> tbls = currentProfile.getTables();
		Set<ProfileTableField> flds = null;

		for (ProfileTableVO tbl : tbls) {
			if (tbl.getId().equals(tableId)) {
				flds = tbl.getFields();
				break;
			}
		}

		long fId = -1;
		String fieldType = null;
		if (flds != null) {
			for (final ProfileTableField f : flds) {
				if (f.getFieldName().equals(filterColumn)) {
					fId = f.getId();
					fieldType = f.getFieldType();
				}
			}
		}
		if (fId == -1) {
			throw new RuntimeException("Column :" + filterColumn + " table: " + tableName + " Not found");
		}

		final ProfileTableFieldFilter filter = new ProfileTableFieldFilter();
		filter.setFieldId(fId);
		filter.setJoinBy(joinBy);
		filter.setFieldName(filterColumn);
		filter.setOperator(filterOperator);
		filter.setCriteria(filterCriteria);
		filter.setCriteria2(filterCriteria2);

		List<ProfileTableFieldFilter> filters = Collections.EMPTY_LIST;

		final List<ProfileTableVO> currentTables = currentProfile.getTables();
		ProfileTableVO tableStoredInDB = null;

		for (final ProfileTableVO t : currentTables) {
			if (t.getId().equals(tableId)) {
				tableStoredInDB = t;
				filters = t.getFilters();
			}
		}


		boolean isFirst = filters == null || filters.size() == 0;
		boolean isValid = true ;

		final ModelAndView mdl = new ModelAndView("addFilter");

		try {
			filter.validate(isFirst, fieldType);
		}catch(DataValidationException dve){
			isValid = false;
			mdl.addObject("errorMsg", dve.getMessage());
		}

		if (filters == null) {
			filters = new ArrayList<ProfileTableFieldFilter>();
			tableStoredInDB.setFilters(filters);
		}
		if(isValid){
			persist(filter);
			filters.add(filter);
		}

		mdl.addObject("table", tableStoredInDB);
		mdl.addObject("tableFound", new Boolean(true));
		mdl.addObject("filterList", filters);
		mdl.addObject("tableId", tableStoredInDB.getId());
		return mdl;

	}

	/** Adds the Table. */
	@RequestMapping(value = "editTableSave", method = RequestMethod.POST)
	@ResponseBody
	public String editTableSave(
			@RequestParam("tableName") final String tableName,
			@RequestParam(value="selectedColumns", required=false) final String[] fieldIds,
			@RequestParam("tableDescription") final String tableDescription,
			final HttpSession session) throws DaoException, SessionExpiredException, DataValidationException {

		final ExtractionProfileVO currentProfile = getCurrentProfile(session);

		if(fieldIds == null || fieldIds.length == 0)
			throw new DataValidationException("Please select at least one field.");

		final SapSystem conn = sapSystemDao.findByPrimaryKey(currentProfile.getSapSystemId());

		// get the table object from SAP, this will contain full list of fields.
		final ProfileTableVO fullVO = getTableFromSAP(tableName, conn);

		// get table object as stored in the database, this will contain
		// only those fields that are currently selected.
		ProfileTableVO table = getTablebyName(tableName, currentProfile);

		List<String> selectedList = Arrays.asList(fieldIds);
		List<String> existingList = new ArrayList<String>();

		Set<ProfileTableField> fields = table.getFields();
		for(ProfileTableField f : fields){
			existingList.add(f.getFieldName());
		}

		// Preparing two lists, list of fields to be added and list of fields to be removed.
		List<ProfileTableField> toBeAdded = new ArrayList<ProfileTableField>();
		List<ProfileTableField> toBeRemoved = new ArrayList<ProfileTableField>();

		int rowSize = 0 ;

		// iterating of the table fields fetched from SAP.
		// this field list will not contains table id and field ids.
		for(ProfileTableField f: fullVO.getFields()){
			String name = f.getFieldName();
			if(selectedList.contains(name) && !existingList.contains(name)){
				toBeAdded.add(f);
			}
			else if(!selectedList.contains(name) && existingList.contains(name)){
				toBeRemoved.add(f);
				Long fid = null;
				// get the field id since field id will not be there in SAP table
				for(ProfileTableField fl : fields){
					if(fl.getFieldName().equals(f.getFieldName())){
						fid = fl.getId();
					}
				}
				// check if the field contains any filters if yes throw a validation exception.
				for(ProfileTableFieldFilter fltr : table.getFilters()){
					if(fltr.getFieldId().equals(fid)){
						throw new DataValidationException("Please delete the filters associated with Field: " + f.getFieldName() + " before removing it.");
					}
				}
			}
			if(selectedList.contains(name)){
				rowSize += f.getFieldLength();
			}
		}
		if(rowSize > 512)
			throw new DataValidationException("Row size is more than 512, Please select less number of fields.");

		for (ProfileTableField f : toBeRemoved) {
			ProfileTableField deleted = null;
			for(ProfileTableField fl : fields){
				if(fl.getFieldName().equals(f.getFieldName())){
					fieldDao.delete(new ProfileTableFieldPk(fl.getId()));
					break;
				}
			}
			if(deleted != null){
				fields.remove(deleted);
			}
		}

		for (ProfileTableField fld : toBeAdded) {
			final ProfileTableField newF = new ProfileTableField();
			newF.setFieldName(fld.getFieldName());
			newF.setFieldType(fld.getFieldType());
			newF.setFieldLength(fld.getFieldLength());
			newF.setDecimals(fld.getDecimals());
			newF.setPosition(fld.getPosition());
			newF.setTableId(table.getId());
			newF.setId(fieldDao.insert(newF).getId());
			fields.add(newF);
		}
		return "Table: " + tableName + " was successfully modified." ;

	}

	/** Adds the Table. */
	@RequestMapping(value = "addTableSave", method = RequestMethod.POST)
	@ResponseBody
	public String addTableSave(
			@RequestParam("tableName") final String tableName,
			@RequestParam(value="selectedColumns", required=false) final String[] fieldIds,
			@RequestParam("tableDescription") final String tableDescription,
			final HttpSession session) throws DaoException, SessionExpiredException, DataValidationException {

		final ExtractionProfileVO currentProfile = getCurrentProfile(session);

		if(fieldIds == null || fieldIds.length == 0)
			throw new DataValidationException("Please select at least one field.");

		final SapSystem conn = sapSystemDao.findByPrimaryKey(currentProfile.getSapSystemId());

		final ProfileTableVO fullVO = getTableFromSAP(tableName, conn);


		// Clone the table and add only the selected fields.
		final ProfileTableVO vo = new ProfileTableVO();
		vo.setTableName(fullVO.getTableName());
		vo.setDescription(fullVO.getDescription());
		vo.setProfileId(currentProfile.getId());

		final Set<ProfileTableField> flds = fullVO.getFields();
		final Set<ProfileTableField> selectedflds = new TreeSet<ProfileTableField>(new Comparator<ProfileTableField>() {
			@Override
			public int compare(ProfileTableField o1, ProfileTableField o2) {
				return new Integer(o1.getPosition()).compareTo(o2.getPosition());
			}
		});

		int rowSize = 0;
		for (final ProfileTableField fld : flds) {
			for (final String fid : fieldIds) {
				if (fid.equals(fld.getFieldName())) {
					final ProfileTableField newF = new ProfileTableField();
					newF.setFieldName(fld.getFieldName());
					newF.setFieldType(fld.getFieldType());
					newF.setFieldLength(fld.getFieldLength());
					newF.setDecimals(fld.getDecimals());
					newF.setPosition(fld.getPosition());
					selectedflds.add(newF);
					rowSize += fld.getFieldLength();
					break;
				}
			}
		}
		if(rowSize > 512)
			throw new DataValidationException("Row size is more than 512, Please select less number of fields.");

		vo.setFields(selectedflds);

		// If profile tables list is null initialize it.
		List<ProfileTableVO> tables = currentProfile.getTables();
		if (tables == null) {
			tables = new ArrayList<ProfileTableVO>();
			currentProfile.setTables(tables);
		}

		// Add table VO to the tables list.
		tables.add(vo);

		// Store the table object in Database.
		persist(vo);

		return "Table: " + tableName + " was successfully added to the current profile." ;

	}

	/**
	 * Gets the list of Extraction profile to populate the Grid.
	 *
	 * @param page the page.
	 * @param rows the rows.
	 * @param sortBy the sort by.
	 * @param sortDirection the sort direction.
	 *
	 * @return the JQ grid data.
	 */
	@RequestMapping(value = "get", method = RequestMethod.GET)
	@ResponseBody
	public JqGridData<ExtractionProfileVO> get(
			@RequestParam("page") final int page,
			@RequestParam("rows") final int rows,
			@RequestParam("sidx") final String sortBy,
			@RequestParam("sord") final String sortDirection)
			throws DaoException {

		String orderby = "1=1 order by ";

		final int res = 0;
		if (sortBy == null || sortBy.equals("") || sortBy.equals("id")) {
			orderby += "id";
		} else {
			orderby += ExtractionProfile.fieldMap.get(sortBy);
		}
		orderby += " " + sortDirection;

		final List<ExtractionProfileVO> vos = getTableProfileVOs(orderby);
		final int totalPages = totalNumberOfPages(vos, rows);
		final int pageNumber = pageNumber(vos, page, rows);
		final int totalRecords = vos.size();

		final List<ExtractionProfileVO> data = GridUtils.dataForPage(vos, page, rows);
		final JqGridData<ExtractionProfileVO> gridData = new JqGridData<ExtractionProfileVO>(
				totalPages, pageNumber, totalRecords, data);
		return gridData;
	}

	/**
	 * Load fields.
	 *
	 * @param page the page.
	 * @param rows the rows.
	 * @param sortBy the sort by.
	 * @param sortDirection the sort direction.
	 * @param session the session.
	 *
	 * @return the JQ grid data.
	 * @throws SessionExpiredException
	 * @throws DaoException
	 */
	@RequestMapping(value = "loadFields", method = RequestMethod.GET)
	@ResponseBody
	public JqGridData<ProfileTableField> getFields(
			@RequestParam final Long tableId,
			@RequestParam(value = "page", required = false) final Integer page,
			@RequestParam(value = "rows", required = false) final Integer rows,
			@RequestParam(value = "sidx", required = false) final String sortBy,
			@RequestParam(value = "sord", required = false) final String sortDirection,
			final HttpSession session) throws SessionExpiredException, DaoException {

		if (tableId != -1l) {

			final ExtractionProfileVO currentProfile = getCurrentProfile(session);

			final String dircn = sortDirection != null ? sortDirection : "asc";
			final String srtby = sortBy != null ? sortBy : "id";
			final int pg = page != null ? page : 1;
			final int rws = rows != null ? rows : 10;

			final List<ProfileTableVO> tables = currentProfile.getTables();

			for (final ProfileTableVO t : tables) {
				if (t.getId().equals(tableId)) {
					if (t.getFields() != null && t.getFields().size() > 0) {
						final Set<ProfileTableField> fields = t.getFields();
						return createFldGridData(pg, rws, new ArrayList(fields));
					}
				}
			}
		}
		return createFldGridData(1, 10, Collections.EMPTY_LIST);
	}

	/**
	 * Load filters.
	 *
	 * @param page the page.
	 * @param rows the rows.
	 * @param sortBy the sort by.
	 * @param sortDirection the sort direction.
	 * @param session the session.
	 *
	 * @return the JQ grid data.
	 * @throws SessionExpiredException
	 * @throws DaoException
	 */
	@RequestMapping(value = "loadFilters", method = RequestMethod.GET)
	@ResponseBody
	public JqGridData<ProfileTableFieldFilter> getFilters(
			@RequestParam final Long tableId,
			@RequestParam(value = "page", required = false) final Integer page,
			@RequestParam(value = "rows", required = false) final Integer rows,
			@RequestParam(value = "sidx", required = false) final String sortBy,
			@RequestParam(value = "sord", required = false) final String sortDirection,
			final HttpSession session) throws SessionExpiredException, DaoException {

		if (tableId != -1l) {
			final ExtractionProfileVO currentProfile = getCurrentProfile(session);
			final String dircn = sortDirection != null ? sortDirection : "asc";
			final String srtby = sortBy != null ? sortBy : "id";
			final int pg = page != null ? page : 1;
			final int rws = rows != null ? rows : 10;

			final List<ProfileTableVO> tables = currentProfile.getTables();

			for (final ProfileTableVO t : tables) {
				if (t.getId().equals(tableId)) {
					if (t.getFilters() != null && t.getFilters().size() > 0) {
						final List<ProfileTableFieldFilter> filters = t
								.getFilters();
						return createFltrGridData(pg, rws, filters);
					}
				}
			}
		}
		return createFltrGridData(1, 10, Collections.EMPTY_LIST);
	}

	/**
	 * Gets the tables.
	 *
	 * @param id the id.
	 * @param page the page.
	 * @param rows the rows.
	 * @param sortBy the sort by.
	 * @param sortDirection the sort direction.
	 *
	 * @return the tables.
	 * */
	@RequestMapping(value = "getTables", method = RequestMethod.GET)
	@ResponseBody
	public JqGridData<ProfileTableVO> getTables(
			@RequestParam("id") final long id,
			@RequestParam(value = "page", required = false) final Integer page,
			@RequestParam(value = "rows", required = false) final Integer rows,
			@RequestParam(value = "sidx", required = false) final String sortBy,
			@RequestParam(value = "sord", required = false) final String sortDirection)
			throws DaoException {

		String whereClause = "profile_id=" + id;
		if (sortBy != null) {
			final String dircn = sortDirection != null ? sortDirection : "asc";
			whereClause += " order by " + ProfileTable.fieldMap.get(sortBy)
					+ " " + dircn;
		}
		final int pg = page != null ? page : 1;
		final int rws = rows != null ? rows : 10;

		final List<ProfileTable> s = tableDao.findDynamic(whereClause);
		final List<ProfileTableVO> v = new ArrayList<ProfileTableVO>();

		for (final ProfileTable profileTable : s) {
			final ProfileTableVO tvo = new ProfileTableVO(profileTable);
			v.add(tvo);
		}
		return createGridData(pg, rws, v);
	}

	/** Adds the Table.
	 * @throws SessionExpiredException */
	@RequestMapping(value = "loadAddFilters", method = RequestMethod.GET)
	public ModelAndView loadAddFilters(
			@RequestParam("tableId") final Long tableId,
			@RequestParam("sapSystemId") final Long sapSystemId,
			final HttpSession session) throws DaoException, SessionExpiredException {

		final ExtractionProfileVO currentProfile = getCurrentProfile(session);

		final List<ProfileTableVO> tables = currentProfile.getTables();
		final ModelAndView mdl = new ModelAndView("addFilter");
		final SapSystem conn = getSapConn(sapSystemId, sapSystemDao.findAll());

		ProfileTableVO table = null;
		String tableName = null;

		List<ProfileTableFieldFilter> filters = new ArrayList<ProfileTableFieldFilter>();

		for (final ProfileTableVO t : tables) {
			if (t.getId().equals(tableId)) {
				table = t;
				tableName = t.getTableName();
			}
			if (t.getFilters() != null && t.getId().equals(tableId)) {
				filters.addAll(t.getFilters());
			}
		}

		mdl.addObject("table", table);
		mdl.addObject("tableId", table.getId());
		mdl.addObject("filterList", filters);
		return mdl;

	}

	/**
	 * Start extraction.
	 *
	 * @param id the profile id.
	 * @param session the session.
	 * @return the response string.
	 */
	@RequestMapping(value = "startExtraction", method = RequestMethod.GET)
	@ResponseBody
	public String startExtraction(@RequestParam("id") final Long id,
			final HttpSession session) throws DaoException {

		ExtractionProfileVO currentProfile = loadProfileFromDB(id);
		if (currentProfile != null) {
			if(currentProfile.getTables() == null || currentProfile.getTables().size() ==0){
				return "No tables added to the current Profile.";
			}
			dataExtractor.performExtraction(currentProfile, null);
			return "Data Extraction Started.";
		}
		return "Profile Not found.";
	}

	/**
	 * Load details.
	 *
	 * @param id the id.
	 * @param session the session.
	 * @return the model and view.
	 */
	@RequestMapping(value = "loadDetails", method = RequestMethod.GET)
	public ModelAndView loadDetails(
			@RequestParam(value = "id", required = false) final Long id,
			final HttpSession session) throws DaoException {

		boolean createNew = false;
		boolean editProfile;
		final ExtractionProfileVO currentProfile;

		final List<DbConnection> dbconns = dbSystemDao.findAll();
		final List<SapSystem> sapconn = sapSystemDao.findAll();

		if (id != null) {
			currentProfile = loadProfileFromDB(id);
			editProfile = false;
		} else {
			currentProfile = new ExtractionProfileVO();
			editProfile = true;
		}
		for (final SapSystem ss : sapconn) {
			if (ss.getId().equals(currentProfile.getSapSystemId())) {
				currentProfile.setSapSystemName(ss.getDestinationName());
				break;
			}
		}
		for (final DbConnection ds : dbconns) {
			if (ds.getId().equals(currentProfile.getDbConnectionId())) {
				currentProfile.setDbConnectionName(ds.getName());
				break;
			}
		}

		session.setAttribute("currentProfile", currentProfile);

		final ModelAndView mdl = new ModelAndView("profileDetails");
		mdl.addObject("editProfile", editProfile);
		mdl.addObject("profile", currentProfile);
		mdl.addObject("dbConnectionList", dbconns);
		mdl.addObject("sapSystemList", sapconn);
		return mdl;
	}

	/**
	 * Load tables.
	 *
	 * @param page the page
	 * @param rows the rows
	 * @param sortBy the sort by
	 * @param sortDirection the sort direction
	 * @param session the session
	 * @return the JQ grid data.
	 * @throws SessionExpiredException
	 * @throws DaoException
	 */
	@RequestMapping(value = "loadTables", method = RequestMethod.GET)
	@ResponseBody
	public JqGridData<ProfileTableVO> loadTables(
			@RequestParam(value = "page", required = false) final Integer page,
			@RequestParam(value = "rows", required = false) final Integer rows,
			@RequestParam(value = "sidx", required = false) final String sortBy,
			@RequestParam(value = "sord", required = false) final String sortDirection,
			final HttpSession session) throws SessionExpiredException, DaoException {

		final String dircn = sortDirection != null ? sortDirection : "asc";
		final String srtby = sortBy != null ? sortBy : "id";
		final int pg = page != null ? page : 1;
		final int rws = rows != null ? rows : 10;

		final ExtractionProfileVO selectedProfile = getCurrentProfile(session);

		if (selectedProfile != null) {

			final List<ProfileTableVO> tables = selectedProfile.getTables();
			if (tables != null && tables.size() > 0) {
				Collections.sort(tables, new Comparator<ProfileTableVO>() {
					@Override
					public int compare(final ProfileTableVO o1,
							final ProfileTableVO o2) {
						int r = 0;
						if ("id".equals(srtby)) {
							r = o1.getId().compareTo(o1.getId());
						}
						if ("profileId".equals(srtby)) {
							r = o1.getProfileId().compareTo(o1.getProfileId());
						}
						if ("tableName".equals(srtby)) {
							r = o1.getTableName().compareTo(o1.getTableName());
						}
						if (!"asc".equals(dircn)) {
							r = 0 - r;
						}
						return r;
					}
				});
				return createGridData(pg, rws, tables);
			}
		}
		return null;
	}

	/** Loads profile - Columns page */
	@RequestMapping(value = "profileColumns", method = RequestMethod.GET)
	public ModelAndView profileColumns(boolean editProfile, final HttpSession session) throws SessionExpiredException, DaoException {

		final ModelAndView mdl = new ModelAndView("profileColumns");
		final ExtractionProfileVO currentProfile = getCurrentProfile(session);

		setTableId(mdl, currentProfile);
		mdl.addObject("editProfile", editProfile);
		mdl.addObject("tables", currentProfile.getTables());
		return mdl;
	}

	/** Loads profile - Filters page */
	@RequestMapping(value = "profileFilters", method = RequestMethod.GET)
	public ModelAndView profileFilters(boolean editProfile, final HttpSession session)
			throws SessionExpiredException, DaoException {

		final ModelAndView mdl = new ModelAndView("profileFilters");
		final ExtractionProfileVO currentProfile = getCurrentProfile(session);

		mdl.addObject("tables", currentProfile.getTables());
		mdl.addObject("editProfile", editProfile);
		setTableId(mdl, currentProfile);
		return mdl;
	}

	/** Loads profile - tables page */
	@RequestMapping(value = "profileTables", method = RequestMethod.GET)
	public ModelAndView profileTables(boolean editProfile, final HttpSession session) {
		final ModelAndView mdl = new ModelAndView("profileTables");
		mdl.addObject("editProfile", editProfile);
		return mdl;
	}

	/** Save profile. */
	@RequestMapping(value = "saveProfile", method = RequestMethod.POST)
	public ModelAndView saveProfile(
			@RequestParam(value = "profileName", required = false) final String profileName,
			@RequestParam(value = "profileDescription", required = false) final String profileDescription,
			@RequestParam(value = "sapSystemId", required = false) final Long sapSystemId,
			@RequestParam(value = "dbConnectionId", required = false) final Long dbConnectionId,
			@RequestParam(value = "continueOnFailure", required = false) Boolean continueOnFailure,
			@RequestParam(value = "writeBatchSize", required = false) final String writeBatchSize,
			@RequestParam(value = "ifTableExists") String ifTableExists,
			final HttpSession session) throws DaoException{

		final List<DbConnection> 	dbconns = dbSystemDao.findAll();
		final List<SapSystem> 		sapconn = sapSystemDao.findAll();

		final ModelAndView mdl = new ModelAndView("profileDetails");
		mdl.addObject("dbConnectionList", dbconns);
		mdl.addObject("sapSystemList", sapconn);

		continueOnFailure = continueOnFailure == null ? false : continueOnFailure.booleanValue();

		String errorMsg =  validate(profileName, sapSystemId, dbConnectionId, writeBatchSize);

		if(errorMsg != null){
			mdl.addObject("profileName", profileName);
			mdl.addObject("profileDescription", profileDescription);
			mdl.addObject("sapSystemId", sapSystemId);
			mdl.addObject("dbConnectionId", dbConnectionId);
			mdl.addObject("continueOnFailure", continueOnFailure);
			mdl.addObject("writeBatchSize", writeBatchSize);
			mdl.addObject("ifTableExists", ifTableExists);
			mdl.addObject("errorMsg", errorMsg);
			mdl.addObject("editProfile", true);
			return mdl;
		}

		String sapSystemName = "";
		String dbConnectionName = "";

		final ExtractionProfileVO profileVO = new ExtractionProfileVO();
		profileVO.setProfileName(profileName);
		profileVO.setProfileDescription(profileDescription);
		for (final SapSystem ss : sapconn) {
			if (ss.getId().equals(sapSystemId)) {
				sapSystemName = ss.getDestinationName();
				break;
			}
		}
		for (final DbConnection ds : dbconns) {
			if (ds.getId().equals(dbConnectionId)) {
				dbConnectionName = ds.getName();
				break;
			}
		}

		profileVO.setSapSystemName(sapSystemName);
		profileVO.setDbConnectionName(dbConnectionName);
		profileVO.setSapSystemId(sapSystemId);
		profileVO.setDbConnectionId(dbConnectionId);
		profileVO.setContinueOnFailure(continueOnFailure);
		profileVO.setWriteBatchSize(Integer.parseInt(writeBatchSize));
		profileVO.setIfTableExists(ifTableExists);

		persist(profileVO);

		session.setAttribute("currentProfile", profileVO);
		mdl.addObject("profile", profileVO);
		return mdl;

	}

	private String validate(final String profileName, final Long sapSystemId,
			final Long dbConnectionId, String writeBatchSize) throws DaoException {
		if(profileName == null || profileName.isEmpty()){
			return "Profiles name cannot be empty or null." ;
		}
		if(profileName.length() >10){
			return "Profiles name cannot be more than 10 chars." ;
		}
		if(!profileName.matches("^[a-zA-Z0-9]*$")){
			return "Profiles name can be Alphanumeric only." ;
		}
		if(sapSystemId == null || sapSystemId == -1){
			return "Please select Source SAP System" ;
		}
		if(dbConnectionId == null || dbConnectionId == -1){
			return "Please select Destination Database." ;
		}
		if(writeBatchSize == null || writeBatchSize.isEmpty()){
			return "Please enter batch size." ;
		}
		try{
			Integer bs = Integer.parseInt(writeBatchSize);
			if(bs < 50 || bs > 30000)
				return "Batch size: " + writeBatchSize + " must be an integer between 50 and 30,000";
		}catch(NumberFormatException e){
			return "Batch size: " + writeBatchSize + " is not a valid integer";
		}
		List<ExtractionProfile> exsits = this.profileDao.findDynamic("profile_name = '" + profileName + "'");
		if(exsits != null && exsits.size() > 0){
			return "A Profile with this name already exists." ;
		}
		return null;
	}

	/** This operation is invoked when "Add Table" button is clicked, 'tableName'
	 * must be passed.
	 * @throws SessionExpiredException */
	@RequestMapping(value = "searchTable", method = RequestMethod.GET)
	public ModelAndView searchTable(
			@RequestParam("tableName") final String tableName,
			@RequestParam("sapSystemId") final Long sapSystemId,
			final HttpSession session) throws DaoException, SessionExpiredException{

		final ModelAndView mdl = new ModelAndView("addTable");
		final SapSystem conn = sapSystemDao.findByPrimaryKey(sapSystemId);
		final ExtractionProfileVO profileVO = getCurrentProfile(session);

		List<ProfileTableVO> tables = profileVO.getTables();
		if(tables != null && tables.size() >0){
			for (ProfileTableVO profileTableVO : tables) {
				if(profileTableVO.getTableName().equals(tableName)){
					mdl.addObject("tableFound", new Boolean(false));
					mdl.addObject("message", "Table already exists in the current profile.");
					return mdl;
				}
			}
		}

		final ProfileTableVO vo = getTableFromSAP(tableName, conn);
		if (vo == null) {
			mdl.addObject("tableFound", new Boolean(false));
			mdl.addObject("message", "Table not found!");
			return mdl;
		}
		mdl.addObject("table", vo);
		mdl.addObject("tableFound", new Boolean(true));
		mdl.addObject("filterList", vo.getFilters());
		mdl.addObject("fieldList", vo.getFields());
		mdl.addObject("actionURL", "addTableSave");
		return mdl;
	}




	@RequestMapping(value = "editTable", method = RequestMethod.GET)
	public ModelAndView editTable(
			@RequestParam("tableId") final Long tableId,
			final HttpSession session) throws DaoException, SessionExpiredException{

		final ModelAndView mdl = new ModelAndView("addTable");
		final ExtractionProfileVO profileVO = getCurrentProfile(session);
		final SapSystem conn = sapSystemDao.findByPrimaryKey(profileVO.getSapSystemId());

		List<ProfileTableVO> tables = profileVO.getTables();
		if(tables != null && tables.size() >0){
			for (ProfileTableVO profileTableVO : tables) {
				if(profileTableVO.getId().equals(tableId)){

					final ProfileTableVO vo = getTableFromSAP(profileTableVO.getTableName(), conn);
					mdl.addObject("table", vo);
					mdl.addObject("tableFound", new Boolean(true));
					mdl.addObject("filterList", profileTableVO.getFilters());
					mdl.addObject("actionURL", "editTableSave");
					mdl.addObject("fieldList", vo.getFields());

					Set<ProfileTableField> selectedFlds = profileTableVO.getFields();
					if(selectedFlds != null && !selectedFlds.isEmpty()){
						List<String> selectedFldNames = new ArrayList<String>();
						for (ProfileTableField profileTableField : selectedFlds) {
							selectedFldNames.add(profileTableField.getFieldName());
						}
						mdl.addObject("selectedfieldList", selectedFldNames);
					}
					return mdl;
				}
			}
		}
		mdl.addObject("tableFound", new Boolean(false));
		mdl.addObject("message", "Table not found!");
		return mdl;
	}


	/** This operation is invoked when "Delete Table" button is clicked, 'tableId'
	 *  must be passed. */
	@RequestMapping(value = "deleteTable", method = RequestMethod.GET)
	@ResponseBody
	public String deleteTable(@RequestParam("tableId") final Long tableId,
			final HttpSession session) throws DaoException, SessionExpiredException{

		final ExtractionProfileVO currentProfile = getCurrentProfile(session);
		List<ProfileTableVO> tables = currentProfile.getTables();
		int tblIndx = -1;
		if(tables != null){
			int count = 0;
			for (ProfileTableVO profileTableVO : tables) {
				if(profileTableVO.getId().equals(tableId)){
					deleteTableData(profileTableVO);
					tblIndx = count;
				}
				count++;
			}
			if(tblIndx != -1){
				tables.remove(tblIndx);
			}
		}
		return "Table deleted successfully.";
	}

	private ExtractionProfileVO getCurrentProfile(final HttpSession session)
			throws SessionExpiredException, DaoException {
		final ExtractionProfileVO currentProfile = (ExtractionProfileVO) session.getAttribute("currentProfile");
		if(currentProfile == null){
			throw new SessionExpiredException("Session Expired! Please start again.");
		}
		return loadProfileFromDB(currentProfile.getId());
	}

	/**
	 * This operation is invoked when "Delete Table" button is clicked, 'tableId'
	 * must be passed. */
	@RequestMapping(value = "deleteFilter", method = RequestMethod.GET)
	@ResponseBody
	public String deleteFilter(
			@RequestParam("filterId") final Long filterId,
			final HttpSession session) throws DaoException, SessionExpiredException{

		final ExtractionProfileVO currentProfile = getCurrentProfile(session);
		List<ProfileTableVO> tables = currentProfile.getTables();

		if(tables != null){
			for (ProfileTableVO profileTableVO : tables) {
				List<ProfileTableFieldFilter> filters = profileTableVO.getFilters();
				if(filters != null){
					int inx = -1, count = 0;
					for (ProfileTableFieldFilter profileTableFieldFilter : filters) {
						if(profileTableFieldFilter.getId().equals(filterId)){
							filterDao.delete(new ProfileTableFieldFilterPk(filterId));
							inx = count;
						}
						count++;
					}
					if(inx != -1){
						filters.remove(inx);
					}
				}
			}
		}
		return "Filter deleted successfully.";
	}


	private void deleteTableData(ProfileTableVO profileTableVO)
			throws DaoException {
		List<ProfileTableFieldFilter> filters = profileTableVO.getFilters();
		if(filters != null){
			for (ProfileTableFieldFilter profileTableFieldFilter : filters) {
				filterDao.delete(new ProfileTableFieldFilterPk(profileTableFieldFilter.getId()));
			}
		}
		Set<ProfileTableField> fields = profileTableVO.getFields();
		if(fields != null){
			for (ProfileTableField profileTableField : fields) {
				fieldDao.delete(new ProfileTableFieldPk(profileTableField.getId()));
			}
		}
		tableDao.delete(new ProfileTablePk(profileTableVO.getId()));
	}

}
