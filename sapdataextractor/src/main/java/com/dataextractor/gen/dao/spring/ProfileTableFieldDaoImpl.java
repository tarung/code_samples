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
import org.springframework.transaction.annotation.Transactional;

import com.dataextractor.gen.dao.ProfileTableFieldDao;
import com.dataextractor.gen.dto.ProfileTableField;
import com.dataextractor.gen.dto.ProfileTableFieldPk;
import com.dataextractor.gen.exceptions.DaoException;

/** The Profile Table Field DAO Implementation. */
@SuppressWarnings("all")
public class ProfileTableFieldDaoImpl extends AbstractDAO implements
		ParameterizedRowMapper<ProfileTableField>, ProfileTableFieldDao {

	/** The JDBC template. */
	protected JdbcTemplate jdbcTemplate;

	/** The data source. */
	@Autowired
	protected DataSource dataSource;

	private List<ProfileTableField> find(final String whereClause)
			throws DaoException {
		try {
			String sql = "SELECT id, field_name, field_type, table_id, "
					+ "field_length, field_decimal, position FROM "
					+ getTableName();
			if (whereClause != null && !whereClause.isEmpty()) {
				sql += " where " + whereClause;
			}
			return jdbcTemplate.query(sql, this);
		} catch (final Exception e) {
			throw new DaoException("Query failed", e);
		}
	}

	/**
	 * Execute insert.
	 *
	 * @param jdbcTemplate the JDBC template
	 * @param dto the DTO
	 * @param INSERT_SQL the insert SQL
	 * @return the long
	 */
	protected long executeInsert(final JdbcTemplate jdbcTemplate,
			final ProfileTableField dto, final String INSERT_SQL) {

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(
					final Connection connection) throws SQLException {

				final PreparedStatement ps = connection.prepareStatement(
						INSERT_SQL, new String[] { "id" });

				ps.setString(1, dto.getFieldName());
				ps.setString(2, dto.getFieldType());
				ps.setLong(3, dto.getTableId());
				ps.setInt(4, dto.getFieldLength());
				ps.setInt(5, dto.getDecimals());
				ps.setInt(6, dto.getPosition());
				return ps;
			}
		}, keyHolder);

		final long id = Long.parseLong("" + keyHolder.getKey());
		return id;
	}

	/** Deletes a single row in the profile_table_field table. */
	@Override
	@Transactional
	public void delete(final ProfileTableFieldPk pk) throws DaoException {
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",
				pk.getId());
	}

	/**
	 * Returns all rows from the profile_table_field table that match the
	 * criteria ''.
	 */
	@Override
	@Transactional
	public List<ProfileTableField> findAll() throws DaoException {
		return find(null);
	}

	/**
	 * Returns all rows from the profile_table_field table that match the
	 * criteria 'id = :id'.
	 */
	@Override
	@Transactional
	public ProfileTableField findByPrimaryKey(final Long id)
			throws DaoException {
		try {
			final List<ProfileTableField> list = jdbcTemplate
					.query("SELECT id, field_name, field_type, table_id, field_length, " +
							"field_decimal, position FROM " + getTableName() + " WHERE id = ?", this, id);
			return list.size() == 0 ? null : list.get(0);
		} catch (final Exception e) {
			throw new DaoException("Query failed", e);
		}
	}

	/**
	 * Returns the rows from the profile_table_field table that matches the
	 * specified primary-key value.
	 */
	@Override
	public ProfileTableField findByPrimaryKey(final ProfileTableFieldPk pk)
			throws DaoException {
		return findByPrimaryKey(pk.getId());
	}

	/** {@inheritDoc} */
	@Override
	public List<ProfileTableField> findDynamic(final String whereClause)
			throws DaoException {
		return find(whereClause);
	}

	/**
	 * Gets the table name.
	 *
	 * @return the table name
	 */
	public String getTableName() {
		return "profile_table_field";
	}

	/** {@inheritDoc} */
	@Override
	@Transactional
	public ProfileTableFieldPk insert(final ProfileTableField dto) {

		final String INSERT_SQL = "INSERT INTO "
				+ getTableName()
				+ " ( field_name, field_type, table_id, field_length, field_decimal, position ) "
				+ "VALUES ( ?, ?, ?, ?, ?, ? )";
		final ProfileTableFieldPk pk = new ProfileTableFieldPk(executeInsert(
				jdbcTemplate, dto, INSERT_SQL));
		return pk;
	}

	/** {@inheritDoc} */
	@Override
	public ProfileTableField mapRow(final ResultSet rs, final int row)
			throws SQLException {
		final ProfileTableField dto = new ProfileTableField();
		dto.setId(new Long(rs.getLong(1)));
		dto.setFieldName(rs.getString(2));
		dto.setFieldType(rs.getString(3));
		dto.setTableId(new Long(rs.getLong(4)));
		dto.setFieldLength(rs.getInt(5));
		dto.setDecimals(rs.getInt(6));
		dto.setPosition(rs.getInt(7));
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

	/** Updates a single row in the profile_table_field table. */
	@Override
	@Transactional
	public void update(final ProfileTableFieldPk pk, final ProfileTableField dto)
			throws DaoException {
		jdbcTemplate
				.update("UPDATE "
						+ getTableName()
						+ " SET id = ?, field_name = ?, field_type = ?, table_id = ?, field_length = ?, "
						+ "field_decimal = ?, position = ?  WHERE id = ?",
						dto.getId(), dto.getFieldName(), dto.getFieldType(),
						dto.getTableId(), dto.getFieldLength(),
						dto.getDecimals(), dto.getPosition(), pk.getId());
	}

}
