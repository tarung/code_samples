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

import com.dataextractor.gen.dao.ProfileTableFieldFilterDao;
import com.dataextractor.gen.dto.ProfileTableField;
import com.dataextractor.gen.dto.ProfileTableFieldFilter;
import com.dataextractor.gen.dto.ProfileTableFieldFilterPk;
import com.dataextractor.gen.dto.ProfileTableFieldPk;
import com.dataextractor.gen.exceptions.DaoException;
import com.dataextractor.gen.exceptions.DaoException;

/** The Profile Table Field Filter DAO. */
@SuppressWarnings("all")
public class ProfileTableFieldFilterDaoImpl extends AbstractDAO implements
        ParameterizedRowMapper<ProfileTableFieldFilter>,
        ProfileTableFieldFilterDao {

    /** The JDBC template. */
    protected JdbcTemplate jdbcTemplate;

    /** The Data Source. */
    @Autowired
    protected DataSource dataSource;


    /** The field DAO. */
    @Autowired
    protected ProfileTableFieldDaoImpl fieldDao ;

    private List<ProfileTableFieldFilter> find(final String whereClause)
            throws DaoException {
        try {
            String sql = "SELECT id, field_id, operator, criteria, criteria2, joinby FROM "
                    + getTableName();
            if (whereClause != null && !whereClause.isEmpty()) {
                sql += " where " + whereClause ;
            }

            return jdbcTemplate.query(sql, this);
        } catch (final Exception e) {
            throw new DaoException("Query failed", e);
        }
    }

    protected long executeInsert(final JdbcTemplate jdbcTemplate,
            final ProfileTableFieldFilter dto, final String INSERT_SQL) {

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            public PreparedStatement createPreparedStatement(
                    final Connection connection) throws SQLException {

                final PreparedStatement ps = connection.prepareStatement(
                        INSERT_SQL, new String[] { "id" });

                ps.setLong(1, dto.getFieldId());
                ps.setString(2, dto.getOperator());
                ps.setString(3, dto.getCriteria());
                ps.setString(4, dto.getCriteria2());
                ps.setString(5, dto.getJoinBy());
                return ps;

            }
        }, keyHolder);

        final long id = Long.parseLong("" + keyHolder.getKey());
        return id;
    }

    /** Deletes a Single row in the profile_table_field_filter table. */
    @Override
    @Transactional
    public void delete(final ProfileTableFieldFilterPk pk)
            throws DaoException {
        jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",
                pk.getId());
    }

    /** Returns all rows from the profile_table_field_filter table that match the criteria ''. */
    @Override
    @Transactional
    public List<ProfileTableFieldFilter> findAll()
            throws DaoException {
        return find(null);
    }

    /** Returns all rows from the profile_table_field_filter table that match
     * the criteria 'id = :id'. */
    @Override
    @Transactional
    public ProfileTableFieldFilter findByPrimaryKey(final Long id)
            throws DaoException {

    	try {

        	final List<ProfileTableFieldFilter> list = jdbcTemplate.query(
                    "SELECT id, field_id, operator, criteria,"
                            + " criteria2, joinby FROM " + getTableName()
                            + " WHERE id = ?", this, id);
            return list.size() == 0 ? null : list.get(0);

        } catch (final Exception e) {
            throw new DaoException("Query failed", e);
        }
    }

    /** Returns the rows from the profile_table_field_filter table that matches
     * the specified primary-key value. */
    @Override
    public ProfileTableFieldFilter findByPrimaryKey(
            final ProfileTableFieldFilterPk pk)
            throws DaoException {
        return findByPrimaryKey(pk.getId());
    }

    /** {@inheritDoc} */
    @Override
    public List<ProfileTableFieldFilter> findDynamic(final String whereClause)
            throws DaoException {
        return find(whereClause);
    }

    /**
     * Gets the table name.
     *
     * @return the table name
     */
    public String getTableName() {
        return "profile_table_field_filter";
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public ProfileTableFieldFilterPk insert(final ProfileTableFieldFilter dto) {

        final String INSERT_SQL = "INSERT INTO " + getTableName()
                + " ( field_id, operator, criteria, criteria2, joinby ) "
                + "VALUES ( ?, ?, ?, ?, ? )";

        final ProfileTableFieldFilterPk pk = new ProfileTableFieldFilterPk(
                executeInsert(jdbcTemplate, dto, INSERT_SQL));
        return pk;
    }

    /** {@inheritDoc} */
    @Override
    public ProfileTableFieldFilter mapRow(final ResultSet rs, final int row)
            throws SQLException {

        final ProfileTableFieldFilter dto = new ProfileTableFieldFilter();
        dto.setId(new Long(rs.getLong(1)));
        dto.setFieldId(new Long(rs.getLong(2)));

        try {
        	ProfileTableField field = fieldDao.findByPrimaryKey(
        			new ProfileTableFieldPk(dto.getFieldId()));
			dto.setFieldName(field.getFieldName());
        } catch (DaoException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}

        dto.setOperator(rs.getString(3));
        dto.setCriteria(rs.getString(4));
        dto.setCriteria2(rs.getString(5));
        dto.setJoinBy(rs.getString(6));

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

    /** Updates a single row in the profile_table_field_filter table. */
    @Override
    @Transactional
    public void update(final ProfileTableFieldFilterPk pk,
            final ProfileTableFieldFilter dto)
            throws DaoException {

        jdbcTemplate.update("UPDATE " + getTableName()
                + " SET id = ?, field_id = ?, operator = ?, criteria = ?, "
                + "criteria2 = ?, joinby = ? WHERE id = ?", dto.getId(),
                dto.getFieldId(), dto.getOperator(), dto.getCriteria(),
                dto.getCriteria2(), dto.getJoinBy(), pk.getId());
    }

}
