package com.dataextractor.gen.dto;

import java.io.Serializable;

/** This class represents the primary key of the extraction_profile table. */
@SuppressWarnings("all")
public class ExtractionProfilePk implements Serializable {

    protected Long id;

    /**
     * The Constructor.
     *
     * @param id the id
     */
    public ExtractionProfilePk(final Long id) {
        this.id = id;
    }

    /**
     * Method 'equals'
     *
     * @param _other
     * @return boolean
     */
    public boolean equals(final Object _other) {
        if (_other == null) {
            return false;
        }

        if (_other == this) {
            return true;
        }

        if (!(_other instanceof ExtractionProfilePk)) {
            return false;
        }

        final ExtractionProfilePk _cast = (ExtractionProfilePk) _other;
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
    public String toString() {
        final StringBuffer ret = new StringBuffer();
        ret.append("com.dataextractor.gen.dto.ExtractionProfilePk: ");
        ret.append("id=" + id);
        return ret.toString();
    }

}
