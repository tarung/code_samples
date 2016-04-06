package com.dataextractor.gen.dto;

import java.io.Serializable;

/**
 * This class represents the primary key of the db_connection table.
 */
@SuppressWarnings("all")
public class DbConnectionPk implements Serializable {
    protected Long id;

    /**
     * Method 'DbConnectionPk'
     *
     * @param id
     */
    public DbConnectionPk(final Long id) {
        this.id = id;
    }

    /**
     * Method 'equals'
     *
     * @param _other
     * @return boolean
     */
    @Override
    public boolean equals(final Object _other) {
        if (_other == null) {
            return false;
        }

        if (_other == this) {
            return true;
        }

        if (!(_other instanceof DbConnectionPk)) {
            return false;
        }

        final DbConnectionPk _cast = (DbConnectionPk) _other;
        if (id == null ? _cast.id != id : !id.equals(_cast.id)) {
            return false;
        }

        return true;
    }

    /**
     * Gets the value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Method 'hashCode'
     *
     * @return int
     */
    @Override
    public int hashCode() {
        int _hashCode = 0;
        if (id != null) {
            _hashCode = 29 * _hashCode + id.hashCode();
        }

        return _hashCode;
    }

    /**
     * Sets the value of id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Method 'toString'
     *
     * @return String
     */
    @Override
    public String toString() {
        final StringBuffer ret = new StringBuffer();
        ret.append("com.dataextractor.gen.dto.DbConnectionPk: ");
        ret.append("id=" + id);
        return ret.toString();
    }

}
