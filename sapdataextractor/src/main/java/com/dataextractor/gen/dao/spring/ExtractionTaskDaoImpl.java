package com.dataextractor.gen.dao.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.dataextractor.gen.dao.ExtractionTaskDao;
import com.dataextractor.gen.dto.DbConnection;
import com.dataextractor.gen.dto.DbConnectionPk;
import com.dataextractor.gen.dto.ExtractionTask;
import com.dataextractor.gen.dto.ExtractionTaskPk;
import com.dataextractor.gen.dto.TaskStatus;
import com.dataextractor.gen.exceptions.DaoException;

/** The Class ExtractionTaskDaoImpl. */
@SuppressWarnings("all")
public class ExtractionTaskDaoImpl extends AbstractDAO implements
		ParameterizedRowMapper<ExtractionTask>, ExtractionTaskDao {

	/** The JDBC template. */
	protected JdbcTemplate jdbcTemplate;

	/** The Data Source. */
	@Autowired
	protected DataSource dataSource;

	private static ParameterizedRowMapper<TaskStatus> ROW_MAPPER =
			new ParameterizedRowMapper<TaskStatus>() {
		@Override
		public TaskStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
			final TaskStatus dto = new TaskStatus();
			dto.setTaskId(new Long(rs.getLong(1)));
			dto.setStatus(rs.getString(2));
			dto.setPercentCompletion(rs.getInt(3));
			return dto;
		}
	};

	/**
	 * Execute Insert.
	 *
	 * @param jdbcTemplate the JDBC template
	 * @param dto the DTO
	 * @param INSERT_SQL the insert SQL
	 * @return the long
	 */
	protected long executeInsert(final JdbcTemplate jdbcTemplate,
			final ExtractionTask dto, final String INSERT_SQL) {

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(
					final Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(
						INSERT_SQL, new String[] { "id" });
				ps.setString(1, dto.getProfileName());
				ps.setString(2, dto.getDescription());
				ps.setString(3, dto.getStatus());
				ps.setInt(4, dto.getPercentCompletion());
				ps.setTimestamp(5, new Timestamp(dto.getStartedOn().getTime()));
				if(dto.getCompletedOn() != null)
					ps.setTimestamp(6, new Timestamp(dto.getCompletedOn().getTime()));
				else {
					ps.setNull(6, Types.TIMESTAMP);
				}
				if(dto.getSchedulerId() != null){
					ps.setLong(7, dto.getSchedulerId());
				}
				else {
					ps.setNull(7, Types.INTEGER);
				}
				return ps;
			}
		}, keyHolder);

		final long id = Long.parseLong("" + keyHolder.getKey());
		return id;
	}

	/**
	 * Deletes a single row in the extraction_history table.
	 *
	 * @param pk the PK
	 */
	@Override
	@Transactional
	public void delete(final ExtractionTaskPk pk)
			throws DaoException {
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",
				pk.getId());
	}

	/**
	 * Returns all rows from the extraction_history table that match the
	 * criteria.
	 *
	 * @return the list
	 */
	@Override
	@Transactional
	public List<ExtractionTask> findAll() throws DaoException {
		return find(null);

	}

	private List<ExtractionTask> find(String whereClause) throws DaoException {
		try {

			String SQL = "SELECT id, profile_name, task_description, "
					+ "status, percentcompletion, startedOn, completedOn, scheduler_id FROM "
					+ getTableName();

			if(whereClause != null && !whereClause.isEmpty()){
				SQL += " where " + whereClause;
			}
			return jdbcTemplate.query(SQL, this);

		} catch (final Exception e) {
			throw new DaoException("Query failed", e);
		}
	}

	/**
	 * Returns the rows from the extraction_history table that matches the
	 * specified primary-key value.
	 *
	 * @param pk the PK
	 * @return the extraction task
	 */
	@Override
	public ExtractionTask findByPrimaryKey(final ExtractionTaskPk pk)
			throws DaoException {
		return findByPrimaryKey(pk.getId());
	}

	/**
	 * Returns all rows from the extraction_history table that match the
	 * criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the extraction task
	 */
	@Override
	@Transactional
	public ExtractionTask findByPrimaryKey(final Long id)
			throws DaoException {
		try {
			final List<ExtractionTask> list = jdbcTemplate
					.query("SELECT id, profile_name, task_description, "
							+ "status, percentcompletion, startedOn, completedOn, scheduler_id  FROM "
							+ getTableName() + " WHERE id = ?", this, id);
			return list.size() == 0 ? null : list.get(0);
		} catch (final Exception e) {
			throw new DaoException("Query failed", e);
		}
	}

	@Transactional
	public TaskStatus getTaskProgress(final Long id) throws DaoException {
		try {
			final List<TaskStatus> list = jdbcTemplate
					.query("SELECT id,  status, percentcompletion FROM "
							+ getTableName() + " WHERE id = ?", ROW_MAPPER, id);
			return list.size() == 0 ? null : list.get(0);
		} catch (final Exception e) {
			throw new DaoException("Query failed", e);
		}
	}

	/**
	 * Gets the table name.
	 *
	 * @return the table name
	 */
	public String getTableName() {
		return "extraction_task";
	}

	/** {@inheritDoc} */
	@Override
	@Transactional
	public ExtractionTaskPk insert(final ExtractionTask dto) {

		String INSERT_SQL = "INSERT INTO " + getTableName()
				+ " (profile_name, task_description, status, "
				+ "percentcompletion, startedOn, completedOn, scheduler_id) "
				+ "VALUES (?,?,?,?,?,?,?)";
		return new ExtractionTaskPk(
				executeInsert(jdbcTemplate, dto, INSERT_SQL));
	}

	/** {@inheritDoc} */
	@Override
	public ExtractionTask mapRow(final ResultSet rs, final int row)
			throws SQLException {

		final ExtractionTask dto = new ExtractionTask();
		dto.setId(new Long(rs.getLong(1)));
		dto.setProfileName(rs.getString(2));
		dto.setDescription(rs.getString(3));
		dto.setStatus(rs.getString(4));
		dto.setPercentCompletion(rs.getInt(5));
		dto.setStartedOn(rs.getTimestamp(6));
		dto.setCompletedOn(rs.getTimestamp(7));
		dto.setSchedulerId(rs.getLong(8));
		return dto;
	}

	/**
	 * Sets the data source.
	 *
	 * @param dataSource the new Data Source
	 */
	public void setDataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Updates a single row in the extraction_history table.
	 *
	 * @param pk the PK.
	 * @param dto the DTO.
	 */
	@Override
	@Transactional
	public void update(final ExtractionTaskPk pk, final ExtractionTask dto)
			throws DaoException {

		jdbcTemplate.update("UPDATE " + getTableName()
				+ " SET id = ?,  profile_name = ?, task_description = ?,"
				+ " status = ?,  percentcompletion = ?,"
				+ " startedOn = ?, completedOn = ?, scheduler_id = ? WHERE id = ?",
				dto.getId(), dto.getProfileName(), dto.getDescription(),
				dto.getStatus(), dto.getPercentCompletion(),
				dto.getStartedOn(), dto.getCompletedOn(), dto.getSchedulerId(), pk.getId());
	}

	@Override
	public List<ExtractionTask> findDynamic(String whereClause) throws DaoException {
		return find(whereClause);
	}

}
