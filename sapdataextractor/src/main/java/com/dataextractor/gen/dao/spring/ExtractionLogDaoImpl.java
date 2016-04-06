package com.dataextractor.gen.dao.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.dataextractor.gen.dao.ExtractionLogDao;
import com.dataextractor.gen.dto.ExtractionLogRecord;
import com.dataextractor.gen.dto.ExtractionLogRecordPk;
import com.dataextractor.gen.exceptions.DaoException;

/** The Extraction log DAO implementation. */
@SuppressWarnings("all")
public class ExtractionLogDaoImpl extends AbstractDAO implements
		ParameterizedRowMapper<ExtractionLogRecord>, ExtractionLogDao {

	/** The JDBC template. */
	protected JdbcTemplate jdbcTemplate;

	/** The data source. */
	protected DataSource dataSource;

	/**
	 * Find ExtractionLogRecord based on a where clause.
	 *
	 * @param whereClause the where clause
	 * @return the list of objects
	 */
	private List<ExtractionLogRecord> find(final String whereClause)
			throws DaoException {
		try {
			String queryString = "SELECT id, task_id, table_name, log_time, "
					+ "message, is_error FROM " + getTableName();
			if (whereClause != null && !whereClause.isEmpty()) {
				queryString += " where " + whereClause;
			}
			return jdbcTemplate.query(queryString, this);
		} catch (final Exception e) {
			throw new DaoException("Query failed", e);
		}
	}

	protected long executeInsert(final JdbcTemplate jdbcTemplate,
			final ExtractionLogRecord dto, final String INSERT_SQL) {

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(
					final Connection connection) throws SQLException {

				final PreparedStatement ps = connection.prepareStatement(
						INSERT_SQL, new String[] { "id" });
				ps.setLong(1, dto.getTaskId());
				ps.setString(2, dto.getTableName());
				ps.setTimestamp(3, new Timestamp(dto.getTimeStamp().getTime()));
				ps.setString(4, dto.getMessage());
				ps.setBoolean(5, dto.isError());
				return ps;
			}
		}, keyHolder);

		final long id = Long.parseLong("" + keyHolder.getKey());
		return id;
	}

	/** Deletes a single row in the sap_system table. */
	@Override
	@Transactional
	public void delete(final ExtractionLogRecordPk pk) throws DaoException {
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",
				pk.getId());
	}

	/** Returns all rows from the sap_system table that match the criteria ''. */
	@Override
	@Transactional
	public List<ExtractionLogRecord> findAll() throws DaoException {
		return find("");
	}

	/**
	 * Returns all rows from the sap_system table that match the criteria
	 * 'id = :id'.
	 */
	@Override
	@Transactional
	public ExtractionLogRecord findByPrimaryKey(final Long id)
			throws DaoException {
		try {
			final List<ExtractionLogRecord> list = jdbcTemplate
					.query("SELECT id, destination_name, description, host_name, sys_nr, user_name, "
							+ "password, language_code, is_pooled, pool_capacity, peak_limit FROM "
							+ getTableName() + " WHERE id = ?", this, id);
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
	public ExtractionLogRecord findByPrimaryKey(final ExtractionLogRecordPk pk)
			throws DaoException {
		return findByPrimaryKey(pk.getId());
	}

	/** {@inheritDoc} */
	@Override
	public List<ExtractionLogRecord> findDynamic(final String whereClause)
			throws DaoException {
		return find(whereClause);
	}

	/** Gets the table name. */
	public String getTableName() {
		return "extraction_log";
	}

	/** Inserts a new ExtractionLogRecord record. */
	@Override
	@Transactional
	public ExtractionLogRecordPk insert(final ExtractionLogRecord dto) {

		final String INSERT_SQL = "INSERT INTO " + getTableName()
				+ " ( task_id, table_name, log_time, message, is_error ) "
				+ "VALUES ( ?, ?, ?, ?, ? )";
		final ExtractionLogRecordPk pk = new ExtractionLogRecordPk(
				executeInsert(jdbcTemplate, dto, INSERT_SQL));
		return pk;
	}

	/** {@inheritDoc} */
	@Override
	public ExtractionLogRecord mapRow(final ResultSet rs, final int row)
			throws SQLException {

		final ExtractionLogRecord dto = new ExtractionLogRecord();
		dto.setId(new Long(rs.getLong(1)));
		dto.setTaskId(rs.getLong(2));
		dto.setTableName(rs.getString(3));
		dto.setTimeStamp(rs.getTimestamp(4));
		dto.setMessage(rs.getString(5));
		dto.setError(rs.getBoolean(6));
		return dto;
	}

	/**
	 * Sets the data source.
	 *
	 * @param dataSource the new data source
	 */
	public void setDataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/** Updates a single row in the sap_system table. */
	@Override
	@Transactional
	public void update(final ExtractionLogRecordPk pk,
			final ExtractionLogRecord dto) throws DaoException {
		jdbcTemplate.update("UPDATE " + getTableName()
				+ " SET id = ?, task_id = ?,  table_name = ?, log_time = ?, "
				+ "message = ?, is_error = ?  WHERE id = ?", dto.getId(),
				dto.getTaskId(), dto.getTableName(), dto.getTimeStamp(),
				dto.getMessage(), dto.getError(), pk.getId());
	}

}
