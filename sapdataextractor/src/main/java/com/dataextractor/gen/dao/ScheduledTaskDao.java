package com.dataextractor.gen.dao;

import java.util.List;

import com.dataextractor.gen.dto.ScheduledTask;
import com.dataextractor.gen.dto.ScheduledTaskPk;
import com.dataextractor.gen.exceptions.DaoException;

public interface ScheduledTaskDao {

	public void delete(ScheduledTaskPk pk) throws DaoException;

    public List<ScheduledTask> findAll() throws DaoException;


    public ScheduledTask findByPrimaryKey(Long id) throws DaoException;

    public ScheduledTask findByPrimaryKey(ScheduledTaskPk pk)
            throws DaoException;

    public List<ScheduledTask> findDynamic(String whereClause)
            throws DaoException;

    public ScheduledTaskPk insert(ScheduledTask dto);

    public void update(ScheduledTaskPk pk, ScheduledTask dto)
            throws DaoException;

}
