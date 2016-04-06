package com.dataextractor.controller;

import static com.dataextractor.util.GridUtils.pageNumber;
import static com.dataextractor.util.GridUtils.totalNumberOfPages;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.dataextractor.conn.DataExtractor;
import com.dataextractor.gen.dao.DbConnectionDao;
import com.dataextractor.gen.dto.DbConnection;
import com.dataextractor.gen.dto.DbConnectionPk;
import com.dataextractor.gen.dto.SapSystem;
import com.dataextractor.gen.exceptions.DaoException;
import com.dataextractor.gen.exceptions.DataValidationException;
import com.dataextractor.model.JqGridData;
import com.dataextractor.util.GridUtils;

@Controller
@RequestMapping("/dbconnection")
@SuppressWarnings("all")
public class DbConnectionController extends AbstractController {

    /** The db connection DAO. */
    @Autowired
    private DbConnectionDao dbSystemDao;

    /** Actually performs add operation */
    protected void add(final DbConnection obj) throws DaoException {
        dbSystemDao.insert(obj);
    }

    /** Actually performs delete operation */
    protected void delete(final DbConnectionPk pk) throws DaoException {
        dbSystemDao.delete(pk);
    }

    /** Actually performs update operation */
    protected void update(final DbConnectionPk pk, final DbConnection sapSystem) throws DaoException {
        dbSystemDao.update(pk, sapSystem);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public JqGridData<DbConnection> get(@RequestParam("page") final int page,
            @RequestParam("rows") final int rows,
            @RequestParam("sidx") final String sortBy,
            @RequestParam("sord") final String sortDirection)
            throws DaoException {

        String orderby = "1=1 order by ";

        final int res = 0;
        if (sortBy == null || sortBy.equals("") || sortBy.equals("id")) {
            orderby += "id";
        } else {
            orderby += DbConnection.fieldMap.get(sortBy);
        }
        orderby += " " + sortDirection;

        final List<DbConnection> rules = dbSystemDao.findDynamic(orderby);
        final int totalPages = totalNumberOfPages(rules, rows);
        final int pageNumber = pageNumber(rules, page, rows);
        final int totalRecords = rules.size();
        final List<DbConnection> data = GridUtils
                .dataForPage(rules, page, rows);
        final JqGridData<DbConnection> gridData = new JqGridData<DbConnection>(
                totalPages, pageNumber, totalRecords, data);
        return gridData;
    }

    /** Handles add, delete, and update operations.
     * @throws DataValidationException */
    @RequestMapping(value = "operations", method = RequestMethod.POST)
    @ResponseBody
    public void operations(@RequestParam("oper") final String oper,
            @ModelAttribute final DbConnection reqData,
            final BindingResult result, final WebRequest request)
            throws DaoException, DataValidationException {
        if (oper.equals("add")) {
            validate(reqData, true);
            testConnection(reqData, true);
        	add(reqData);
        } else {
            final String rId = request.getParameter("id");
            final DbConnectionPk pk = new DbConnectionPk(Long.parseLong(rId));
            if (oper.equals("del")) {
                delete(pk);
            } else if (oper.equals("edit")) {
            	validate(reqData, false);
                testConnection(reqData, true);
                update(pk, reqData);
            }
        }
    }

    @RequestMapping(value = "loadAdd", method = RequestMethod.GET)
	public ModelAndView loadAdd( @RequestParam(value = "edit", required = false) final Boolean isEdit,
			@RequestParam(value = "id", required = false) final Long id)
			throws DaoException {

		ModelAndView mdl = new ModelAndView("addDbConfiguration");
		if (isEdit != null && isEdit) {
			DbConnection dbconn = dbSystemDao.findByPrimaryKey(id);
			mdl.addObject("dbConnection", dbconn);
			mdl.addObject("oper", "edit");
			mdl.addObject("id", id);
		}
		else{
			mdl.addObject("oper", "add");
		}
		return mdl;
	}

	private void validate(final DbConnection reqData, boolean checkForDuplicateName) throws DataValidationException, DaoException{

		checkString(reqData.getName(), "Connection name", 4, 12, true);
    	checkString(reqData.getDriverClassName(), "Driver Class Name", 1, 50, false);
    	checkString(reqData.getUserName(), "User name", 1, 50, false);
    	checkString(reqData.getUrl(), "URL", 1, 50, false);
    	if(checkForDuplicateName){
	    	List<DbConnection> exists = this.dbSystemDao.findDynamic("name = '" +reqData.getName() + "'");
	    	if(exists != null && !exists.isEmpty()){
	    		throw new DataValidationException("DB connection by this name already exists.");
	    	}
    	}
     }

	@RequestMapping(value = "testConnection", method = RequestMethod.POST)
	@ResponseBody
	public String testConnection(@ModelAttribute final DbConnection reqData,
			@RequestParam(value = "skipValidation", required = false) final boolean skipValidation)
			throws DaoException, DaoException, DataValidationException {

		if(!skipValidation){
			validate(reqData, false);
		}
		DataExtractor ext = new DataExtractor();
		Connection conn = null;
		try {
			conn = ext.getConnection(reqData);
			conn.getMetaData();
			return "Test successful.";
		} catch (Exception e) {
			throw new DataValidationException("Test Failed: " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// NOP
				}
			}
		}
	}

}
