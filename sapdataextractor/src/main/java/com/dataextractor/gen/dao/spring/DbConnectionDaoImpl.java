package com.dataextractor.gen.dao.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.dataextractor.gen.dao.DbConnectionDao;
import com.dataextractor.gen.dto.DbConnection;
import com.dataextractor.gen.dto.DbConnectionPk;
import com.dataextractor.gen.exceptions.DaoException;

/** The DbConnectionDao Implementation class. */
@SuppressWarnings("all")
public class DbConnectionDaoImpl extends AbstractDAO implements
        ParameterizedRowMapper<DbConnection>, DbConnectionDao {

    /** The JDBC template. */
    protected JdbcTemplate jdbcTemplate;

    /** The data source. */
    @Autowired
    protected DataSource dataSource;

    /**
     * Finds DbConnection records based on a where clause.
     *
     * @param whereClause the where clause
     * @return the list
     * @throws DaoException the db connection DAO exception
     */
    private List<DbConnection> find(final String whereClause)
            throws DaoException {
        try {
            String queryString = "SELECT id, name, description, driverClassName, url, "
                    + "userName, password FROM " + getTableName();
            if (whereClause != null && !whereClause.isEmpty()) {
                queryString += " where " + whereClause;
            }
            return jdbcTemplate.query(queryString, this);
        } catch (final Exception e) {
            throw new DaoException("Query failed", e);
        }
    }

    protected long executeInsert(final JdbcTemplate jdbcTemplate,
            final DbConnection dto, final String INSERT_SQL) {

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            public PreparedStatement createPreparedStatement(
                    final Connection connection) throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(
                        INSERT_SQL, new String[] { "id" });
                ps.setString(1, dto.getName());
                ps.setString(2, dto.getDescription());
                ps.setString(3, dto.getDriverClassName());
                ps.setString(4, dto.getUrl());
                ps.setString(5, dto.getUserName());
                ps.setString(6, dto.getPassword());
                return ps;
            }
        }, keyHolder);

        final long id = Long.parseLong("" + keyHolder.getKey());
        return id;
    }

    /** Deletes a single row in the db_connection table. */
    @Override
    @Transactional
    public void delete(final DbConnectionPk pk) throws DaoException {
        try {
			jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",
			        pk.getId());
		} catch (DataIntegrityViolationException diE) {
			throw new DaoException("Cannot delete DB connection because of exsiting profiles." +
					" Please delete profiles associated to this DB connection before proceeding.");
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
    }

    /** Returns all rows from the db_connection table that match the criteria ''. */
    @Override
    @Transactional
    public List<DbConnection> findAll() throws DaoException {
        return find("");

    }

    /** Returns the rows from the db_connection table that matches the specified primary-key value. */
    @Override
    public DbConnection findByPrimaryKey(final DbConnectionPk pk)
            throws DaoException {
        return findByPrimaryKey(pk.getId());
    }

    /** Returns all rows from the db_connection table that match the criteria 'id = :id'. */
    @Override
    @Transactional
    public DbConnection findByPrimaryKey(final Long id)
            throws DaoException {
        try {
            final List<DbConnection> list = jdbcTemplate.query(
                    "SELECT id, name, description, driverClassName, url, "
                            + "userName, password FROM " + getTableName()
                            + " WHERE id = ?", this, id);
            return list.size() == 0 ? null : list.get(0);
        } catch (final Exception e) {
            throw new DaoException("Query failed", e);
        }
    }

    /** Returns all rows from the db_connection table that match the criteria 'url=:url'. */
    @Override
    @Transactional
    public List<DbConnection> findbyURL(final String url)
            throws DaoException {
        try {
            return jdbcTemplate.query(
                    "SELECT id, name, description, driverClassName, url, userName, "
                            + "password FROM " + getTableName()
                            + " WHERE url=? ORDER BY id", this, url);
        } catch (final Exception e) {
            throw new DaoException("Query failed", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<DbConnection> findDynamic(final String whereClause)
            throws DaoException {
        return find(whereClause);
    }

    /**
     * Gets the table name.
     *
     * @return the table name
     */
    public String getTableName() {
        return "db_connection";
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public DbConnectionPk insert(final DbConnection dto) {

        final String INSERT_SQL = "INSERT INTO " + getTableName()
                + " (name, description, driverClassName, url, userName, password ) "
                + "VALUES (?,?, ?, ?, ?, ? )";
        return new DbConnectionPk(executeInsert(jdbcTemplate, dto, INSERT_SQL));
    }

    /** {@inheritDoc} */
    @Override
    public DbConnection mapRow(final ResultSet rs, final int row)
            throws SQLException {

        final DbConnection dto = new DbConnection();
        dto.setId(new Long(rs.getLong(1)));
        dto.setName(rs.getString(2));
        dto.setDescription(rs.getString(3));
        dto.setDriverClassName(rs.getString(4));
        dto.setUrl(rs.getString(5));
        dto.setUserName(rs.getString(6));
        dto.setPassword(rs.getString(7));
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

    /** Updates a single row in the db_connection table. */
    @Override
    @Transactional
    public void update(final DbConnectionPk pk, final DbConnection dto)
            throws DaoException {
        jdbcTemplate
                .update("UPDATE "
                        + getTableName()
                        + " SET id = ?, name = ?, description = ?,"
                        + " driverClassName = ?, url = ?, userName = ?, password = ? WHERE id = ?",
                        dto.getId(), dto.getName(), dto.getDescription(),
                        dto.getDriverClassName(), dto.getUrl(),
                        dto.getUserName(), dto.getPassword(), pk.getId());
    }

}
