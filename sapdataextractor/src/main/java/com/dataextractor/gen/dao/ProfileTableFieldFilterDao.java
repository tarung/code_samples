package com.dataextractor.gen.dao;

import java.util.List;

import com.dataextractor.gen.dto.ProfileTableFieldFilter;
import com.dataextractor.gen.dto.ProfileTableFieldFilterPk;
import com.dataextractor.gen.exceptions.DaoException;

public interface ProfileTableFieldFilterDao {
	/**
	 * Deletes a single row in the profile_table_field_filter table.
	 */
	public void delete(ProfileTableFieldFilterPk pk) throws DaoException;

	/**
	 * Returns all rows from the profile_table_field_filter table that match the
	 * criteria ''.
	 */
	public List<ProfileTableFieldFilter> findAll() throws DaoException;

	/**
	 * Returns all rows from the profile_table_field_filter table that match the
	 * criteria 'id = :id'.
	 */
	public ProfileTableFieldFilter findByPrimaryKey(Long id)
			throws DaoException;

	/**
	 * Returns the rows from the profile_table_field_filter table that matches
	 * the specified primary-key value.
	 */
	public ProfileTableFieldFilter findByPrimaryKey(ProfileTableFieldFilterPk pk)
			throws DaoException;

	/**
	 * Returns rows from the profile_table_field_filter table that match a
	 * criteria.
	 */
	public List<ProfileTableFieldFilter> findDynamic(String whereClause)
			throws DaoException;

	/**
	 * Method 'insert'
	 *
	 * @param dto
	 * @return ProfileTableFieldFilterPk
	 */
	public ProfileTableFieldFilterPk insert(ProfileTableFieldFilter dto);

	/**
	 * Updates a single row in the profile_table_field_filter table.
	 */
	public void update(ProfileTableFieldFilterPk pk, ProfileTableFieldFilter dto)
			throws DaoException;

}
