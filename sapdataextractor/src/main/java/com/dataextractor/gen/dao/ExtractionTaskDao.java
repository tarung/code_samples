package com.dataextractor.gen.dao;

import com.dataextractor.gen.dao.ExtractionTaskDao;
import com.dataextractor.gen.dto.ExtractionTask;
import com.dataextractor.gen.dto.ExtractionTaskPk;
import com.dataextractor.gen.dto.TaskStatus;
import com.dataextractor.gen.exceptions.DaoException;
import java.util.List;

public interface ExtractionTaskDao
{
	public ExtractionTaskPk insert(ExtractionTask dto);

	/** Updates a single row in the extraction_history table. */
	public void update(ExtractionTaskPk pk, ExtractionTask dto) throws DaoException;

	/** Deletes a single row in the extraction_history table. */
	public void delete(ExtractionTaskPk pk) throws DaoException;

	/** Returns all rows from the extraction_history table that match the criteria 'id = :id'. */
	public ExtractionTask findByPrimaryKey(Long id) throws DaoException;

	/** Returns all rows from the extraction_history table that match the criteria ''*/
	public List<ExtractionTask> findAll() throws DaoException;


	public List<ExtractionTask> findDynamic(String whereClause) throws DaoException;

	/** Returns the rows from the extraction_history table that matches the specified primary-key value. */
	public ExtractionTask findByPrimaryKey(ExtractionTaskPk pk) throws DaoException;

	/** Gets the task status for progress bar.*/
	public TaskStatus getTaskProgress(final Long id) throws DaoException;
}
