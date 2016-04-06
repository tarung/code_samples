package com.dataextractor.controller;

import static com.dataextractor.util.GridUtils.pageNumber;
import static com.dataextractor.util.GridUtils.totalNumberOfPages;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

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

import com.dataextractor.conn.DestinationDataProvider;
import com.dataextractor.conn.SAPUtil;
import com.dataextractor.gen.dao.SapSystemDao;
import com.dataextractor.gen.dto.SapSystem;
import com.dataextractor.gen.dto.SapSystemPk;
import com.dataextractor.gen.exceptions.DaoException;
import com.dataextractor.gen.exceptions.DataValidationException;
import com.dataextractor.model.JqGridData;
import com.dataextractor.util.GridUtils;

@Controller
@RequestMapping("/sapsystem")
@SuppressWarnings("all")
public class SapConnectionController extends AbstractController {

	/** The sap system DAO. */
	@Autowired
	private SapSystemDao sapSystemDao;

	/** The SAP utility Object. */
	@Autowired
	private SAPUtil sapUtil;

	private static Map<String, String> languagMap;

	/** Actually performs add operation */
	protected void add(final SapSystem reqData) throws DaoException {
		sapSystemDao.insert(reqData);
	}

	/** Actually performs delete operation */
	protected void delete(final SapSystemPk pk) throws DaoException {
		sapSystemDao.delete(pk);
	}

	/** Actually performs update operation */
	protected void update(final SapSystemPk pk, final SapSystem reqData) throws DaoException {
		sapSystemDao.update(pk, reqData);
	}

	@RequestMapping(value = "get", method = RequestMethod.GET)
	@ResponseBody
	public JqGridData<SapSystem> get(@RequestParam("page") final int page, @RequestParam("rows") final int rows,
			@RequestParam("sidx") final String sortBy, @RequestParam("sord") final String sortDirection)
			throws DaoException {

		String orderby = "1=1 order by ";

		final int res = 0;
		if (sortBy == null || sortBy.equals("") || sortBy.equals("id")) {
			orderby += "id";
		}
		else {
			orderby += SapSystem.fieldMap.get(sortBy);
		}
		orderby += " " + sortDirection;

		final List<SapSystem> rules = sapSystemDao.findDynamic(orderby);
		final int totalPages = totalNumberOfPages(rules, rows);
		final int pageNumber = pageNumber(rules, page, rows);
		final int totalRecords = rules.size();
		final List<SapSystem> data = GridUtils.dataForPage(rules, page, rows);

		final JqGridData<SapSystem> gridData = new JqGridData<SapSystem>(totalPages, pageNumber, totalRecords, data);

		return gridData;
	}

	@RequestMapping(value = "loadAdd", method = RequestMethod.GET)
	public ModelAndView loadAdd(@RequestParam(value = "edit", required = false) final Boolean isEdit,
			@RequestParam(value = "id", required = false) final Long id) throws DaoException {

		final ModelAndView mdl = new ModelAndView("addSapSystem");
		initLangs();
		mdl.addObject("languagMap", languagMap);

		if (isEdit != null && isEdit) {
			final SapSystem sap = sapSystemDao.findByPrimaryKey(id);
			mdl.addObject("sapSystem", sap);
			mdl.addObject("oper", "edit");
			mdl.addObject("id", id);
		}
		else {
			mdl.addObject("oper", "add");
		}
		return mdl;
	}

	private void initLangs() {
		if (languagMap == null) {
			final Properties props = new Properties();
			languagMap = new HashMap<String, String>();
			try {
				final InputStream is = this.getClass().getClassLoader().getResourceAsStream("lang.properties");
				props.load(is);
			}
			catch (final IOException ex) {
				throw new RuntimeException(ex.getMessage(), ex);
			}
			for (final Entry<Object, Object> e : props.entrySet()) {
				languagMap.put((String) e.getValue(), (String) e.getKey());
			}
		}
	}

	private void validate(final SapSystem reqData, final boolean checkDuplicateName) throws DataValidationException,
			DaoException {

		checkString(reqData.getDestinationName(), "Destination name", 4, 12, true);
		checkString(reqData.getHostName(), "Host name", 4, 50, false);
		checkString(reqData.getUserName(), "User name", 4, 50, false);

		if (reqData.isIsPooled()) {
			final Integer poolCapacity = reqData.getPoolCapacity();
			if (poolCapacity == null) {
				throw new DataValidationException("Please enter Pool Capacity.");
			}
			if (poolCapacity < 2 || poolCapacity > 20) {
				throw new DataValidationException("Pool Capacity must be a number between 2 and 20");
			}
			final Integer peakLimit = reqData.getPeakLimit();
			if (peakLimit == null) {
				throw new DataValidationException("Please enter Peak Limit.");
			}
			if (peakLimit < 2 || peakLimit > 20) {
				throw new DataValidationException("Peak Limit must be a number between 2 and 20");
			}
		}
		if (checkDuplicateName) {
			final List<SapSystem> exists = sapSystemDao.findDynamic("destination_name = '"
					+ reqData.getDestinationName() + "'");
			if (exists != null && !exists.isEmpty()) {
				throw new DataValidationException("Destination name already exists.");
			}
		}
	}

	@RequestMapping(value = "operations", method = RequestMethod.POST)
	@ResponseBody
	public void operations(@RequestParam("oper") final String oper, @ModelAttribute final SapSystem reqData,
			final BindingResult result, final WebRequest request) throws DaoException, DaoException,
			DataValidationException {

		if (oper.equals("add")) {
			validate(reqData, true);
			testConnection(reqData, true);
			add(reqData);
		}
		else {
			final String rId = request.getParameter("id");
			final SapSystemPk pk = new SapSystemPk(Long.parseLong(rId));
			final DestinationDataProvider provider = DestinationDataProvider.getInstance(sapSystemDao);
			if (oper.equals("del")) {
				delete(pk);
				provider.removeTempDestination(reqData.getDestinationName());
			}
			else if (oper.equals("edit")) {
				validate(reqData, false);
				testConnection(reqData, true);
				update(pk, reqData);
				provider.updatedDestination(reqData.getDestinationName());
			}
		}
	}

	@RequestMapping(value = "testConnection", method = RequestMethod.POST)
	@ResponseBody
	public String testConnection(@ModelAttribute final SapSystem reqData,
			@RequestParam(value = "skipValidation", required = false) final boolean skipValidation)
			throws DaoException, DaoException, DataValidationException {

		if (!skipValidation) {
			validate(reqData, false);
		}
		final String result = sapUtil.testConnection(reqData);
		if (result == null) {
			return "Test successful.";
		}
		throw new DataValidationException("Test Failed: " + result);
	}

}
