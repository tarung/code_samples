package com.dataextractor.gen.dao.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.dataextractor.gen.dao.SapSystemDao;
import com.dataextractor.gen.dto.SapSystem;
import com.dataextractor.gen.dto.SapSystemPk;
import com.dataextractor.gen.exceptions.DaoException;

/** The Sap System DAO Impl. */
@SuppressWarnings("all")
public class SapSystemDaoImpl extends AbstractDAO implements
        ParameterizedRowMapper<SapSystem>, SapSystemDao {

    /** The jdbc template. */
    protected JdbcTemplate jdbcTemplate;

    /** The data source. */
    protected DataSource dataSource;

    /**
     * Find SapSystem based on a where clause.
     *
     * @param whereClause the where clause
     * @return the list of objects
     */
    private List<SapSystem> find(final String whereClause)
            throws DaoException {
        try {

        	String queryString =
        			"SELECT id, destination_name, description, host_name, "
                    + "sys_nr, user_name, client_number, password, language_code, "
                    + "is_pooled, pool_capacity, peak_limit FROM "
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
            final SapSystem dto, final String INSERT_SQL) {

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            public PreparedStatement createPreparedStatement(
                    final Connection connection) throws SQLException {

            	final PreparedStatement ps = connection.prepareStatement(
                        INSERT_SQL, new String[] { "id" });

            	ps.setString(1, dto.getDestinationName());
                ps.setString(2, dto.getDescription());
                ps.setString(3, dto.getHostName());
                ps.setString(4, dto.getSysNr());
                ps.setString(5, dto.getUserName());
                ps.setInt(6, dto.getClientNumber());
                ps.setString(7, dto.getPassword());
                ps.setString(8, dto.getLanguageCode());
                ps.setBoolean(9, dto.isIsPooled());
                if(dto.getPoolCapacity() != null){
                	ps.setInt(10, dto.getPoolCapacity());
                }else{
                	ps.setNull(10, Types.INTEGER);
                }
                if(dto.getPeakLimit() != null){
                	ps.setInt(11, dto.getPeakLimit());
                }else{
                	ps.setNull(11, Types.INTEGER);
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
	public void delete(final SapSystemPk pk) throws DaoException {

		try {
			jdbcTemplate.update("DELETE FROM " + getTableName()
					+ " WHERE id = ?", pk.getId());
		} catch (DataIntegrityViolationException diE) {
			throw new DaoException("Cannot delete SAP configuration because of exsiting profiles." +
					" Please delete profile before proceeding.");
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}

    /** Returns all rows from the sap_system table that match the criteria ''. */
    @Override
    @Transactional
    public List<SapSystem> findAll() throws DaoException {
        return find("");
    }

    /** Returns all rows from the sap_system table that match the criteria 'id = :id'. */
    @Override
    @Transactional
    public SapSystem findByPrimaryKey(final Long id)
            throws DaoException {
        try {
            final List<SapSystem> list = jdbcTemplate
                    .query("SELECT id, destination_name, description, host_name, sys_nr, user_name, client_number, "
                            + "password, language_code, is_pooled, pool_capacity, peak_limit FROM "
                            + getTableName() + " WHERE id = ?", this, id);
            return list.size() == 0 ? null : list.get(0);
        } catch (final Exception e) {
            throw new DaoException("Query failed", e);
        }
    }

    /** Returns the rows from the sap_system table that matches the specified primary-key value. */
    @Override
    public SapSystem findByPrimaryKey(final SapSystemPk pk)
            throws DaoException {
        return findByPrimaryKey(pk.getId());
    }

    /** {@inheritDoc} */
    @Override
    public List<SapSystem> findDynamic(final String whereClause)
            throws DaoException {
        return find(whereClause);
    }

    /** Gets the table name. */
    public String getTableName() {
        return "sap_system";
    }

    /** Inserts a new SapSystem record. */
    @Override
    @Transactional
    public SapSystemPk insert(final SapSystem dto) {

        final String INSERT_SQL = "INSERT INTO "
                + getTableName()
                + " ( destination_name, description, host_name, sys_nr, user_name, client_number, "
                + "password, language_code, is_pooled, pool_capacity, peak_limit ) "
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
        final SapSystemPk pk = new SapSystemPk(executeInsert(jdbcTemplate, dto,
                INSERT_SQL));
        return pk;
    }

    /** {@inheritDoc} */
    @Override
    public SapSystem mapRow(final ResultSet rs, final int row)
            throws SQLException {

        final SapSystem dto = new SapSystem();
        dto.setId(new Long(rs.getLong(1)));
        dto.setDestinationName(rs.getString(2));
        dto.setDescription(rs.getString(3));
        dto.setHostName(rs.getString(4));
        dto.setSysNr(rs.getString(5));
        dto.setUserName(rs.getString(6));
        dto.setClientNumber(rs.getInt(7));
        dto.setPassword(rs.getString(8));
        dto.setLanguageCode(rs.getString(9));
        dto.setIsPooled(rs.getBoolean(10));
        dto.setPoolCapacity(new Integer(rs.getInt(11)));
        dto.setPeakLimit(rs.getInt(12));
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
    public void update(final SapSystemPk pk, final SapSystem dto)
            throws DaoException {
        jdbcTemplate
                .update("UPDATE "
                        + getTableName()
                        + " SET id = ?, destination_name = ?,  description = ?, host_name = ?, "
                        + "sys_nr = ?, user_name = ?, client_number = ?, password = ?, language_code = ?, "
                        + "is_pooled = ?, pool_capacity = ?, peak_limit = ? WHERE id = ?",
                        dto.getId(), dto.getDestinationName(),
                        dto.getDescription(), dto.getHostName(),
                        dto.getSysNr(), dto.getUserName(), dto.getClientNumber(), dto.getPassword(),
                        dto.getLanguageCode(), dto.isIsPooled(),
                        dto.getPoolCapacity(), dto.getPeakLimit(), pk.getId());
    }

}
