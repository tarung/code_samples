package com.dataextractor.gen.dto;

import static java.lang.String.format;

import java.io.Serializable;

import com.dataextractor.conn.SAPDataType;
import com.dataextractor.gen.exceptions.DataValidationException;

/** The Profile Table Field Filter. */
@SuppressWarnings("all")
public class ProfileTableFieldFilter implements Serializable {

	/** This attribute maps to the column id in the
	 * profile_table_field_filter table. */
	protected Long id;

	/** This attribute maps to the column field_id in the
	 * profile_table_field_filter table. */
	protected Long fieldId;

	/** The operator. */
	protected String operator;

	/** The criteria. */
	protected String criteria;

	/** The criteria2. */
	protected String criteria2;

	/** The 'join By' field (AND/OR), optional. */
	protected String joinBy;

	/** The field name for display only. */
	protected String fieldName;


	/**
	 * Method 'createPk'
	 *
	 * @return ProfileTableFieldFilterPk
	 */
	public ProfileTableFieldFilterPk createPk() {
		return new ProfileTableFieldFilterPk(id);
	}

	/** @return the criteria */
	public String getCriteria() {
		return criteria;
	}

	/** @return the criteria2 */
	public String getCriteria2() {
		return criteria2;
	}

	/**
	 * Gets the field id.
	 *
	 * @return the field id
	 */
	public Long getFieldId() {
		return fieldId;
	}

	/** @return the fieldName */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Method 'getId'
	 *
	 * @return Long
	 */
	public Long getId() {
		return id;
	}

	/** The 'join By' field (AND/OR), optional. */
	public String getJoinBy() {
		return joinBy;
	}

	/** @return the operator */
	public String getOperator() {
		return operator;
	}

	/** @param criteria the criteria to set */
	public void setCriteria(final String criteria) {
		this.criteria = criteria;
	}

	/** @param criteria2 the criteria2 to set */
	public void setCriteria2(final String criteria2) {
		this.criteria2 = criteria2;
	}

	/**
	 * Sets the field id.
	 *
	 * @param fieldId the new field id
	 */
	public void setFieldId(final Long fieldId) {
		this.fieldId = fieldId;
	}

	/** @param fieldName the fieldName to set */
	public void setFieldName(final String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/** Sets the 'join By' field (AND/OR), optional. */
	public void setJoinBy(final String joinBy) {
		this.joinBy = joinBy;
	}

	/** @param operator the operator to set */
	public void setOperator(final String operator) {
		this.operator = operator;
	}


	public void validate(boolean isFirst, String fieldType) throws DataValidationException{
		if(this.fieldName == null){
			throw new DataValidationException("Field name is not specified.");
		}
		if(!isFirst && this.joinBy == null){
			throw new DataValidationException("Join operator AND/OR is not specified for " + this.fieldName);
		}if(operator == null){
			throw new DataValidationException("Filter operator is not specified for " + this.fieldName);
		}if(criteria == null){
			throw new DataValidationException("Filter criteria is not specified for " + this.fieldName);
		}if("Between".equalsIgnoreCase(operator) && criteria2 == null){
			throw new DataValidationException("Filter criteria2 must be specified for " + this.fieldName);
		}if(!"Between".equalsIgnoreCase(operator) && !(criteria2 == null || criteria2.trim().isEmpty())){
			throw new DataValidationException("Filter criteria2 is valid only in case of 'Between' operator. ");
		}
		String message = SAPDataType.valueOf(fieldType).isValidFilter(this);
		if(message != null){
			throw new DataValidationException(message);
		}
	}

	public String getFilterString(){

		String filterString = "";
		if(this.joinBy != null){
			filterString += this.joinBy + " ";
		}
		filterString += this.fieldName + " " ;
		filterString += this.operator + " " ;
		filterString += "'" + this.getCriteria() + "' " ;
		if(getCriteria2() != null && !getCriteria2().isEmpty()){
			filterString += "'" + this.getCriteria2()+ "' ";
		}
		return filterString;
	}

	@Override
	public String toString() {
		return format("\t Filter=%s", getFilterString());
	}
}
