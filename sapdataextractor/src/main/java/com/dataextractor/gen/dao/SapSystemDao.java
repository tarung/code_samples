package com.dataextractor.gen.dao;

import java.util.List;

import com.dataextractor.gen.dto.SapSystem;
import com.dataextractor.gen.dto.SapSystemPk;
import com.dataextractor.gen.exceptions.DaoException;

public interface SapSystemDao {
    /**
     * Deletes a single row in the sap_system table.
     */
    public void delete(SapSystemPk pk) throws DaoException;

    /**
     * Returns all rows from the sap_system table that match the criteria ''.
     */
    public List<SapSystem> findAll() throws DaoException;

    /**
     * Returns all rows from the sap_system table that match the criteria 'id = :id'.
     */
    public SapSystem findByPrimaryKey(Long id) throws DaoException;

    /**
     * Returns the rows from the sap_system table that matches the specified primary-key value.
     */
    public SapSystem findByPrimaryKey(SapSystemPk pk)
            throws DaoException;

    /**
     * Returns the rows from the sap_system table that matches the specified whereClause value.
     */
    public List<SapSystem> findDynamic(String whereClause)
            throws DaoException;

    /**
     * Method 'insert'
     *
     * @param dto
     * @return SapSystemPk
     */
    public SapSystemPk insert(SapSystem dto);

    /**
     * Updates a single row in the sap_system table.
     */
    public void update(SapSystemPk pk, SapSystem dto)
            throws DaoException;

}
