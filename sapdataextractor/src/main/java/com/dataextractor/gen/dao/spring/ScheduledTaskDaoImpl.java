package com.dataextractor.gen.dao.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.dataextractor.gen.dao.ScheduledTaskDao;
import com.dataextractor.gen.dto.ScheduledTask;
import com.dataextractor.gen.dto.ScheduledTaskPk;
import com.dataextractor.gen.exceptions.DaoException;

/** The Sap System DAO Impl. */
@SuppressWarnings("all")
public class ScheduledTaskDaoImpl extends AbstractDAO implements
		ParameterizedRowMapper<ScheduledTask>, ScheduledTaskDao {

	protected JdbcTemplate jdbcTemplate;

	protected DataSource dataSource;

	/**
	 * Find ScheduledTask based on a where clause.
	 *
	 * @param whereClause
	 *            the where clause
	 * @return the list of objects
	 */
	private List<ScheduledTask> find(final String whereClause)
			throws DaoException {
		try {
			String queryString = "SELECT id, name, profile_id, start_date, end_date, "
					+ "repeat_after, last_execution_time, next_execution_time FROM "
					+ getTableName();
			if (whereClause != null && !whereClause.isEmpty()) {
				queryString += " where " + whereClause;
			}
			return jdbcTemplate.query(queryString, this);

		} catch (final Exception e) {
			throw new DaoException("Query failed", e);
		}
	}

	protected long executeInsert(final JdbcTemplate jdbcTemplate,
			final ScheduledTask dto, final String INSERT_SQL) {

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(
					final Connection connection) throws SQLException {

				final PreparedStatement ps = connection.prepareStatement(
						INSERT_SQL, new String[] { "id" });
				ps.setString(1, dto.getName());
				ps.setLong(2, dto.getProfileId());
				ps.setTimestamp(3, new Timestamp(dto.getStartDate().getTime()));

				if(dto.getEndDate() != null){
					ps.setTimestamp(4, new Timestamp(dto.getEndDate().getTime()));
				}else{
					ps.setNull(4, Types.TIMESTAMP);
				}

				ps.setLong(5, dto.getRepeatAfter());

				if(dto.getLastExecutionTime() != null){
					ps.setTimestamp(6, new Timestamp(dto.getLastExecutionTime().getTime()));
				}else{
					ps.setNull(6, Types.TIMESTAMP);
				}

				if(dto.getNextExecutionTime() != null){
					ps.setTimestamp(7, new Timestamp(dto.getNextExecutionTime().getTime()));
				}else{
					ps.setNull(7, Types.TIMESTAMP);
				}
				return ps;
			}
		}, keyHolder);

		final long id = Long.parseLong("" + keyHolder.getKey());
		return id;
	}

	/** Deletes a single row in the sap_system table. */
	@Override
	@Transactional
	public void delete(final ScheduledTaskPk pk) throws DaoException {
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",
				pk.getId());
	}

	/** Returns all rows from the sap_system table that match the criteria ''. */
	@Override
	@Transactional
	public List<ScheduledTask> findAll() throws DaoException {
		return find("");
	}

	/**
	 * Returns all rows from the sap_system table that match the criteria 'id =
	 * :id'.
	 */
	@Override
	@Transactional
	public ScheduledTask findByPrimaryKey(final Long id) throws DaoException {
		try {
			final List<ScheduledTask> list = find("id=" +id);
			return list.size() == 0 ? null : list.get(0);
		} catch (final Exception e) {
			throw new DaoException("Query failed", e);
		}
	}

	/**
	 * Returns the rows from the sap_system table that matches the specified
	 * primary-key value.
	 */
	@Override
	public ScheduledTask findByPrimaryKey(final ScheduledTaskPk pk)
			throws DaoException {
		return findByPrimaryKey(pk.getId());
	}

	/** {@inheritDoc} */
	@Override
	public List<ScheduledTask> findDynamic(final String whereClause)
			throws DaoException {
		return find(whereClause);
	}

	/** Gets the table name. */
	public String getTableName() {
		return "scheduled_task";
	}

	/** Inserts a new ScheduledTask record. */
	@Override
	@Transactional
	public ScheduledTaskPk insert(final ScheduledTask dto) {

		final String INSERT_SQL = "INSERT INTO "
				+ getTableName()
				+ " ( name, profile_id, start_date, end_date, "
				+ " repeat_after, last_execution_time, next_execution_time ) "
				+ "VALUES ( ?, ?, ?, ?, ?, ?, ?)";
		final ScheduledTaskPk pk = new ScheduledTaskPk(executeInsert(
				jdbcTemplate, dto, INSERT_SQL));
		return pk;
	}

	/** {@inheritDoc} */
	@Override
	public ScheduledTask mapRow(final ResultSet rs, final int row)
			throws SQLException {
		final ScheduledTask dto = new ScheduledTask();
		dto.setId(rs.getLong(1));
		dto.setName(rs.getString(2));
		dto.setProfileId(rs.getLong(3));
		dto.setStartDate(rs.getTimestamp(4));
		dto.setEndDate(rs.getTimestamp(5));
		dto.setRepeatAfter(rs.getLong(6));
		dto.setLastExecutionTime(rs.getTimestamp(7));
		dto.setNextExecutionTime(rs.getTimestamp(8));
		return dto;
	}

	/**
	 * Sets the data source.
	 *
	 * @param dataSource
	 *            the new data source
	 */
	public void setDataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/** Updates a single row in the sap_system table. */
	@Override
	@Transactional
	public void update(final ScheduledTaskPk pk, final ScheduledTask dto)
			throws DaoException {
		jdbcTemplate
				.update("UPDATE "
						+ getTableName()
						+ " SET id = ?, name = ?,  profile_id = ?, start_date = ?, end_date = ?,"
						+ " repeat_after = ?, last_execution_time = ?, next_execution_time = ?"
						+ " WHERE id = ?", dto.getId(), dto.getName(),
						dto.getProfileId(), dto.getStartDate(), dto.getEndDate(),
						dto.getRepeatAfter(), dto.getLastExecutionTime(),
						dto.getNextExecutionTime(), pk.getId());

	}

}
