package com.dataextractor.gen.dao;

import java.util.List;

import com.dataextractor.gen.dto.ProfileTable;
import com.dataextractor.gen.dto.ProfileTablePk;
import com.dataextractor.gen.exceptions.DaoException;

public interface ProfileTableDao {
	/**
	 * Deletes a single row in the profile_table table.
	 */
	public void delete(ProfileTablePk pk) throws DaoException;

	/**
	 * Returns all rows from the profile_table table that match the criteria ''.
	 */
	public List<ProfileTable> findAll() throws DaoException;

	/**
	 * Returns all rows from the profile_table table that match the criteria 'id
	 * = :id'.
	 */
	public ProfileTable findByPrimaryKey(Long id) throws DaoException;

	/**
	 * Returns the rows from the profile_table table that matches the specified
	 * primary-key value.
	 */
	public ProfileTable findByPrimaryKey(ProfileTablePk pk) throws DaoException;

	/**
	 * Returns all rows from the extraction_profile table that match a criteria.
	 *
	 * @throws ProfileTableDaoException
	 */
	public List<ProfileTable> findDynamic(String whereClause)
			throws DaoException;

	/**
	 * Method 'insert'
	 *
	 * @param dto
	 * @return ProfileTablePk
	 */
	public ProfileTablePk insert(ProfileTable dto);

	/**
	 * Updates a single row in the profile_table table.
	 */
	public void update(ProfileTablePk pk, ProfileTable dto) throws DaoException;

}
