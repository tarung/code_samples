package com.dataextractor.gen.dao;

import java.util.List;

import com.dataextractor.gen.dto.ProfileTableField;
import com.dataextractor.gen.dto.ProfileTableFieldPk;
import com.dataextractor.gen.exceptions.DaoException;

public interface ProfileTableFieldDao {
	/**
	 * Deletes a single row in the profile_table_field table.
	 */
	public void delete(ProfileTableFieldPk pk) throws DaoException;

	/**
	 * Returns all rows from the profile_table_field table that match the
	 * criteria ''.
	 */
	public List<ProfileTableField> findAll() throws DaoException;

	/**
	 * Returns all rows from the profile_table_field table that match the
	 * criteria 'id = :id'.
	 */
	public ProfileTableField findByPrimaryKey(Long id) throws DaoException;

	/**
	 * Returns the rows from the profile_table_field table that matches the
	 * specified primary-key value.
	 */
	public ProfileTableField findByPrimaryKey(ProfileTableFieldPk pk)
			throws DaoException;

	/**
	 * Returns rows from the profile_table_field table that match a criteria.
	 */
	public List<ProfileTableField> findDynamic(String whereClause)
			throws DaoException;

	/**
	 * Method 'insert'
	 *
	 * @param dto
	 * @return ProfileTableFieldPk
	 */
	public ProfileTableFieldPk insert(ProfileTableField dto);

	/**
	 * Updates a single row in the profile_table_field table.
	 */
	public void update(ProfileTableFieldPk pk, ProfileTableField dto)
			throws DaoException;

}
