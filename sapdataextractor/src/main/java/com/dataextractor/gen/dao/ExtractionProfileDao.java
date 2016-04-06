package com.dataextractor.gen.dao;

import java.util.List;

import com.dataextractor.gen.dto.ExtractionProfile;
import com.dataextractor.gen.dto.ExtractionProfilePk;
import com.dataextractor.gen.exceptions.DaoException;

public interface ExtractionProfileDao {

	/** Deletes a single row in the extraction_profile table. */
	public void delete(ExtractionProfilePk pk) throws DaoException;

	/**
	 * Returns all rows from the extraction_profile table that match the
	 * criteria ''.
	 */
	public List<ExtractionProfile> findAll() throws DaoException;

	/**
	 * Returns the rows from the extraction_profile table that matches the
	 * specified primary-key value.
	 */
	public ExtractionProfile findByPrimaryKey(ExtractionProfilePk pk)
			throws DaoException;

	/**
	 * Returns all rows from the extraction_profile table that match the
	 * criteria 'id = :id'.
	 */
	public ExtractionProfile findByPrimaryKey(Long id) throws DaoException;

	/**
	 * Returns all rows from the extraction_profile table that match a criteria.
	 *
	 * @throws DaoException
	 */
	public List<ExtractionProfile> findDynamic(String whereClause)
			throws DaoException;

	/**
	 * Method 'insert'
	 *
	 * @param dto
	 * @return ExtractionProfilePk
	 */
	public ExtractionProfilePk insert(ExtractionProfile dto);

	/** Updates a single row in the extraction_profile table. */
	public void update(ExtractionProfilePk pk, ExtractionProfile dto)
			throws DaoException;

}
