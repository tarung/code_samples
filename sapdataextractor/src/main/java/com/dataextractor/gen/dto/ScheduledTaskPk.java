package com.dataextractor.gen.dto;

import java.io.Serializable;

@SuppressWarnings("all")
public class ScheduledTaskPk implements Serializable {
    protected Long id;

    public ScheduledTaskPk(final Long id) {
        this.id = id;
    }

    public boolean equals(final Object _other) {
        if (_other == null) {
            return false;
        }

        if (_other == this) {
            return true;
        }

        if (!(_other instanceof ScheduledTaskPk)) {
            return false;
        }

        final ScheduledTaskPk _cast = (ScheduledTaskPk) _other;
        if (id == null ? _cast.id != id : !id.equals(_cast.id)) {
            return false;
        }

        return true;
    }

    public Long getId() {
        return id;
    }

    public int hashCode() {
        int _hashCode = 0;
        if (id != null) {
            _hashCode = 29 * _hashCode + id.hashCode();
        }

        return _hashCode;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String toString() {
        final StringBuffer ret = new StringBuffer();
        ret.append("com.dataextractor.gen.dto.SapSystemPk: ");
        ret.append("id=" + id);
        return ret.toString();
    }

}
