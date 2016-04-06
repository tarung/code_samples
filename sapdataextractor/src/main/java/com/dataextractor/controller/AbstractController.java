package com.dataextractor.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dataextractor.gen.exceptions.DaoException;
import com.dataextractor.gen.exceptions.DataValidationException;
import com.dataextractor.gen.exceptions.SessionExpiredException;

/** The Abstract Controller class that extends all the controller, and provides a
 * common set of methods. */
public class AbstractController {

	/** The data source. */
	@Autowired
	private DriverManagerDataSource dataSource;

	protected void checkString(String data, String fieldName, int minL,
			int maxL, boolean checkPattern) throws DataValidationException {

		if (data == null) {
			throw new DataValidationException("Please enter " + fieldName);
		} else if (data.length() < minL || data.length() > maxL) {
			throw new DataValidationException(fieldName
					+ " must be between 4 to 12 chars");
		}
		else if (checkPattern && !data.matches("^[a-zA-Z0-9]*$")) {
			throw new DataValidationException(fieldName
					+ " can be Alphanumeric only.");
		}
	}

	/**
	 * Handle DAO exception.
	 *
	 * @param ex the exception class.
	 * @param response the response object
	 * @return the response body string
	 *
	 * @throws IOException the IO Exception.
	 */
	@ExceptionHandler(DaoException.class)
	@ResponseBody()
	public String handleDaoException(final DaoException ex,
			final HttpServletResponse response) throws IOException {

		ex.printStackTrace(System.err);
		response.setStatus(500);
		return ex.getMessage();
	}

	/**
	 * Handle Session exception.
	 *
	 * @param ex the exception class.
	 * @param response the response object
	 * @return the response body string
	 *
	 * @throws IOException the IO Exception.
	 */
	@ExceptionHandler(SessionExpiredException.class)
	@ResponseBody()
	public String handleSessionException(final SessionExpiredException ex,
			final HttpServletResponse response) throws IOException {
		ex.printStackTrace(System.err);
		response.setStatus(500);
		return ex.getMessage();
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody()
	public String handleRuntimeException(final RuntimeException ex,
			final HttpServletResponse response) throws IOException {
		ex.printStackTrace(System.err);
		response.setStatus(500);
		return ex.getMessage();
	}


	/**
	 * Handle data exception.
	 *
	 * @param ex the exception class.
	 * @param response the response object
	 * @return the response body string
	 *
	 * @throws IOException the IO Exception.
	 */
	@ExceptionHandler(DataValidationException.class)
	@ResponseBody()
	public String handleDataException(final DataValidationException ex,
			final HttpServletResponse response) throws IOException {
//		ex.printStackTrace(System.err);
		response.setStatus(500);
		return ex.getMessage();
	}
}
