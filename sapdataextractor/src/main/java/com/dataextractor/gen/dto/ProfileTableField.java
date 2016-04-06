package com.dataextractor.gen.dto;

import static java.lang.String.format;

import java.io.Serializable;

/** The Profile Table Field. */
@SuppressWarnings("all")
public class ProfileTableField implements Serializable{

    /** This attribute maps to the column id in the profile_table_field table. */
    protected Long id;

    /** This attribute maps to the column field_name in the profile_table_field table. */
    protected String fieldName;

    /** This attribute maps to the column field_type in the profile_table_field table. */
    protected String fieldType;

    /** This attribute maps to the column table_id in the profile_table_field table. */
    protected Long tableId;

    /** The field length. */
    protected int fieldLength;

    /** The length after decimal. */
    protected int decimals;

    /** The position of this field. */
    protected int position;

    /** The position of this field. */
    public int getPosition() {
		return position;
	}

    /** The position of this field. */
    public void setPosition(int position) {
		this.position = position;
	}

	public int getDecimals() {
		return decimals;
	}

	public void setDecimals(int decimals) {
		this.decimals = decimals;
	}

	/**
     * Method 'createPk'
     *
     * @return ProfileTableFieldPk
     */
    public ProfileTableFieldPk createPk() {
        return new ProfileTableFieldPk(id);
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

        if (!(_other instanceof ProfileTableField)) {
            return false;
        }

        final ProfileTableField _cast = (ProfileTableField) _other;
        if (id == null ? _cast.id != id : !id.equals(_cast.id)) {
            return false;
        }
        if (fieldName == null ? _cast.fieldName != fieldName : !fieldName
                .equals(_cast.fieldName)) {
            return false;
        }
        if (fieldType == null ? _cast.fieldType != fieldType : !fieldType
                .equals(_cast.fieldType)) {
            return false;
        }
        if (tableId == null ? _cast.tableId != tableId : !tableId
                .equals(_cast.tableId)) {
            return false;
        }
        return true;
    }

    /**
     * @return the fieldLength
     */
    public int getFieldLength() {
        return fieldLength;
    }

    /**
     * Method 'getFieldName'
     *
     * @return String
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Method 'getFieldType'
     *
     * @return String
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * Method 'getId'
     *
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Method 'getTableId'
     *
     * @return Long
     */
    public Long getTableId() {
        return tableId;
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

        if (fieldName != null) {
            _hashCode = 29 * _hashCode + fieldName.hashCode();
        }

        if (fieldType != null) {
            _hashCode = 29 * _hashCode + fieldType.hashCode();
        }

        if (tableId != null) {
            _hashCode = 29 * _hashCode + tableId.hashCode();
        }

        return _hashCode;
    }

    /**
     * @param fieldLength the fieldLength to set
     */
    public void setFieldLength(final int fieldLength) {
        this.fieldLength = fieldLength;
    }

    /**
     * Method 'setFieldName'
     *
     * @param fieldName
     */
    public void setFieldName(final String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Method 'setFieldType'
     *
     * @param fieldType
     */
    public void setFieldType(final String fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * Method 'setId'
     *
     * @param id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Method 'setTableId'
     *
     * @param tableId
     */
    public void setTableId(final Long tableId) {
        this.tableId = tableId;
    }

	@Override
	public String toString() {
		return format("\t Field Name=%s", fieldName);
	}

}
