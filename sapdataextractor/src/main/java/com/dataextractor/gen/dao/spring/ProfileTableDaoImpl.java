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

import com.dataextractor.gen.dao.ProfileTableDao;
import com.dataextractor.gen.dto.ProfileTable;
import com.dataextractor.gen.dto.ProfileTablePk;
import com.dataextractor.gen.exceptions.DaoException;

/** The Profile Table DAO. */
@SuppressWarnings("all")
public class ProfileTableDaoImpl extends AbstractDAO implements
        ParameterizedRowMapper<ProfileTable>, ProfileTableDao {

    /** The JDBC template. */
    protected JdbcTemplate jdbcTemplate;

    /** The data source. */
    @Autowired
    protected DataSource dataSource;

    private List<ProfileTable> find(final String whereClause)
            throws DaoException {

        try {
            String queryString = "SELECT id, table_name, profile_id, description FROM "
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
            final ProfileTable dto, final String INSERT_SQL) {

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            public PreparedStatement createPreparedStatement(
                    final Connection connection) throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(
                        INSERT_SQL, new String[] { "id" });

                ps.setString(1, dto.getTableName());
                ps.setLong(2, dto.getProfileId());
                ps.setString(3, dto.getDescription());

                return ps;
            }
        }, keyHolder);

        final long id = Long.parseLong("" + keyHolder.getKey());
        return id;
    }

    /** Deletes a single row in the profile_table table. */
    @Override
    @Transactional
    public void delete(final ProfileTablePk pk) throws DaoException {
        jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",
                pk.getId());
    }

    /** Returns all rows from the profile_table table that match the criteria ''. */
    @Override
    @Transactional
    public List<ProfileTable> findAll() throws DaoException {
        return find("");

    }

    /** Returns all rows from the profile_table table that match the criteria 'id = :id'. */
    @Override
    @Transactional
    public ProfileTable findByPrimaryKey(final Long id)
            throws DaoException {
        try {
            final List<ProfileTable> list = jdbcTemplate.query(
                    "SELECT id, table_name, profile_id, description FROM "
                            + getTableName() + " WHERE id = ?", this, id);
            return list.size() == 0 ? null : list.get(0);
        } catch (final Exception e) {
            throw new DaoException("Query failed", e);
        }

    }

    /** Returns the rows from the profile_table table that matches the specified primary-key value. */
    @Override
    public ProfileTable findByPrimaryKey(final ProfileTablePk pk)
            throws DaoException {
        return findByPrimaryKey(pk.getId());
    }

    /** {@inheritDoc}
     * @throws DaoException */
    @Override
    public List<ProfileTable> findDynamic(final String whereClause)
            throws DaoException {
        return find(whereClause);
    }

    /**
     * Gets the table name.
     *
     * @return the table name
     */
    public String getTableName() {
        return "profile_table";
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public ProfileTablePk insert(final ProfileTable dto) {

        final String INSERT_SQL = "INSERT INTO " + getTableName()
                + " ( table_name, profile_id, description ) VALUES ( ?, ?, ? )";
        final ProfileTablePk pk = new ProfileTablePk(executeInsert(
                jdbcTemplate, dto, INSERT_SQL));
        return pk;
    }

    /** {@inheritDoc} */
    @Override
    public ProfileTable mapRow(final ResultSet rs, final int row)
            throws SQLException {
        final ProfileTable dto = new ProfileTable();
        dto.setId(new Long(rs.getLong(1)));
        dto.setTableName(rs.getString(2));
        dto.setProfileId(new Long(rs.getLong(3)));
        dto.setDescription(rs.getString(4));
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

    /** Updates a single row in the profile_table table. */
    @Override
    @Transactional
    public void update(final ProfileTablePk pk, final ProfileTable dto)
            throws DaoException {
        jdbcTemplate
                .update("UPDATE "
                        + getTableName()
                        + " SET id = ?, table_name = ?, profile_id = ?, description = ? WHERE id = ?",
                        dto.getId(), dto.getTableName(), dto.getProfileId(),
                        dto.getDescription(), pk.getId());
    }

}
