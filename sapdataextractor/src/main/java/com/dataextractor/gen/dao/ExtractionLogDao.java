package com.dataextractor.gen.dao;

import java.util.List;

import com.dataextractor.gen.dto.ExtractionLogRecord;
import com.dataextractor.gen.dto.ExtractionLogRecordPk;
import com.dataextractor.gen.exceptions.DaoException;

public interface ExtractionLogDao {

    /** Deletes a single row in the extraction_log table. */
    public void delete(ExtractionLogRecordPk pk) throws DaoException;

    /** Returns all rows from the extraction_log table that match the criteria ''. */
    public List<ExtractionLogRecord> findAll() throws DaoException;

    /** Returns all rows from the extraction_log table that match the criteria 'id = :id'. */
    public ExtractionLogRecord findByPrimaryKey(Long id) throws DaoException;

    /** Returns the rows from the extraction_log table that matches the specified primary-key value. */
    public ExtractionLogRecord findByPrimaryKey(ExtractionLogRecordPk pk)
            throws DaoException;

    /** Returns the rows from the extraction_log table that matches the specified whereClause value. */
    public List<ExtractionLogRecord> findDynamic(String whereClause)
            throws DaoException;

    /** Inserts a single row in the extraction_log table. */
    public ExtractionLogRecordPk insert(ExtractionLogRecord dto);

    /** Updates a single row in the extraction_log table. */
    public void update(ExtractionLogRecordPk pk, ExtractionLogRecord dto)
            throws DaoException;

}
