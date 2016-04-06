package com.dataextractor.gen.dao.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dataextractor.gen.dao.ExtractionProfileDao;
import com.dataextractor.gen.dto.ExtractionProfile;
import com.dataextractor.gen.dto.ExtractionProfilePk;
import com.dataextractor.gen.exceptions.DaoException;

/** The Extraction Profile DAO */
@SuppressWarnings("all")
public class ExtractionProfileDaoImpl extends AbstractDAO implements
		ParameterizedRowMapper<ExtractionProfile>, ExtractionProfileDao {

	/** The JDBC template. */
	protected JdbcTemplate jdbcTemplate;

	/** The data source. */
	@Autowired
	protected DataSource dataSource;

	/**
	 * Finds ExtractionProfile records based on a whereClause.
	 *
	 * @param whereClause
	 *            the where clause
	 * @return the list
	 * @throws DaoException
	 *             the extraction profile DAO exception
	 */
	private List<ExtractionProfile> find(final String whereClause)
			throws DaoException {
		try {
			String queryString = "SELECT id, profile_name, profile_description, "
					+ " db_connection_id, sap_system_id,  "
					+ "continue_on_failure, write_batch_size, if_table_exists FROM "
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
			final ExtractionProfile dto, final String INSERT_SQL) {

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(
					final Connection connection) throws SQLException {

				final PreparedStatement ps = connection.prepareStatement(
						INSERT_SQL, new String[] { "id" });
				ps.setString(1, dto.getProfileName());
				ps.setString(2, dto.getProfileDescription());
				ps.setLong(3, dto.getDbConnectionId());
				ps.setLong(4, dto.getSapSystemId());
				ps.setBoolean(5, dto.isContinueOnFailure());
				ps.setInt(6, dto.getWriteBatchSize());
				ps.setString(7, dto.getIfTableExists());
				return ps;
			}
		}, keyHolder);

		final long id = Long.parseLong("" + keyHolder.getKey());
		return id;
	}

	/** Deletes a single row in the extraction_profile table. */
	@Override
	@Transactional
	public void delete(final ExtractionProfilePk pk) throws DaoException {
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",
				pk.getId());
	}

	/**
	 * Returns all rows from the extraction_profile table that match the
	 * criteria.
	 */
	@Override
	@Transactional
	public List<ExtractionProfile> findAll() throws DaoException {
		return find("");
	}

	/**
	 * Returns the rows from the extraction_profile table that matches the
	 * specified primary-key value.
	 */
	@Override
	public ExtractionProfile findByPrimaryKey(final ExtractionProfilePk pk)
			throws DaoException {
		return findByPrimaryKey(pk.getId());
	}

	/**
	 * Returns all rows from the extraction_profile table that match the
	 * criteria 'id = :id'.
	 */
	@Override
	@Transactional
	public ExtractionProfile findByPrimaryKey(final Long id)
			throws DaoException {
		try {
			final List<ExtractionProfile> list = jdbcTemplate.query(
					"SELECT id, profile_name, profile_description, "
							+ "db_connection_id, sap_system_id, "
							+ "continue_on_failure, write_batch_size, "
							+ "if_table_exists FROM " + getTableName()
							+ " WHERE id = ?", this, id);
			return list.size() == 0 ? null : list.get(0);
		} catch (final Exception e) {
			throw new DaoException("Query failed", e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws DaoException
	 */
	@Override
	public List<ExtractionProfile> findDynamic(final String whereClause)
			throws DaoException {
		return find(whereClause);
	}

	/** Gets the table name. */
	public String getTableName() {
		return "extraction_profile";
	}

	/** Method 'insert' Extraction Profile. */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ExtractionProfilePk insert(final ExtractionProfile dto) {

		final String INSERT_SQL = "INSERT INTO " + getTableName()
				+ " ( profile_name, profile_description, "
				+ "db_connection_id, sap_system_id, continue_on_failure,"
				+ " write_batch_size, if_table_exists ) "
				+ "VALUES ( ?, ?, ?, ?, ?, ?, ? )";

		final long id = executeInsert(jdbcTemplate, dto, INSERT_SQL);
		final ExtractionProfilePk pk = new ExtractionProfilePk(id);
		return pk;
	}

	/** Method 'mapRow' */
	@Override
	public ExtractionProfile mapRow(final ResultSet rs, final int row)
			throws SQLException {

		final ExtractionProfile dto = new ExtractionProfile();
		dto.setId(new Long(rs.getLong(1)));
		dto.setProfileName(rs.getString(2));
		dto.setProfileDescription(rs.getString(3));
		dto.setDbConnectionId(rs.getLong(4));
		dto.setSapSystemId(rs.getLong(5));
		dto.setContinueOnFailure(rs.getBoolean(6));
		dto.setWriteBatchSize(rs.getInt(7));
		dto.setIfTableExists(rs.getString(8));
		return dto;
	}

	/** Sets the data source. */
	public void setDataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/** Updates a single row in the extraction_profile table. */
	@Override
	@Transactional
	public void update(final ExtractionProfilePk pk, final ExtractionProfile dto)
			throws DaoException {
		jdbcTemplate
				.update("UPDATE "
						+ getTableName()
						+ "SET id = ?, profile_name = ?, "
						+ "profile_description = ?, db_connection_id = ?, "
						+ "sap_system_id = ?, continue_on_failure = ?, "
						+ "write_batch_size = ?, if_table_exists = ? WHERE id = ?",
						dto.getId(), dto.getProfileName(),
						dto.getProfileDescription(), dto.getDbConnectionId(),
						dto.getSapSystemId(), dto.isContinueOnFailure(),
						dto.getWriteBatchSize(), dto.getIfTableExists(),
						pk.getId());
	}

}
