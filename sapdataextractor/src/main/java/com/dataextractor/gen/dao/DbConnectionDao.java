package com.dataextractor.gen.dao;

import java.util.List;

import com.dataextractor.gen.dto.DbConnection;
import com.dataextractor.gen.dto.DbConnectionPk;
import com.dataextractor.gen.exceptions.DaoException;

public interface DbConnectionDao {
	/**
	 * Deletes a single row in the db_connection table.
	 */
	public void delete(DbConnectionPk pk) throws DaoException;

	/**
	 * Returns all rows from the db_connection table''.
	 */
	public List<DbConnection> findAll() throws DaoException;

	/**
	 * Returns the rows from the db_connection table that matches the specified
	 * primary-key value.
	 */
	public DbConnection findByPrimaryKey(DbConnectionPk pk) throws DaoException;

	/**
	 * Returns all rows from the db_connection table that match the criteria 'id
	 * = :id'.
	 */
	public DbConnection findByPrimaryKey(Long id) throws DaoException;

	/**
	 * Returns all rows from the db_connection table that match the criteria
	 * 'url=:url'.
	 */
	public List<DbConnection> findbyURL(String url) throws DaoException;

	/**
	 * Returns all rows from the db_connection table that match the criteria ''.
	 *
	 * @param whereclause
	 * @return results
	 * @throws DaoException
	 */
	public List<DbConnection> findDynamic(String orderby) throws DaoException;

	/**
	 * Method 'insert'
	 *
	 * @param dto
	 * @return DbConnectionPk
	 */
	public DbConnectionPk insert(DbConnection dto);

	/**
	 * Updates a single row in the db_connection table.
	 */
	public void update(DbConnectionPk pk, DbConnection dto) throws DaoException;

}
