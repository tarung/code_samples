package com.dataextractor.model;

import java.util.List;
import java.util.Set;

import com.dataextractor.gen.dto.ProfileTable;
import com.dataextractor.gen.dto.ProfileTableField;
import com.dataextractor.gen.dto.ProfileTableFieldFilter;

/** The Class Profile Table VO. */
public class ProfileTableVO extends ProfileTable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The fields. */
	private Set<ProfileTableField> fields;

	/** The filters. */
	private List<ProfileTableFieldFilter> filters;

	/** Instantiates a new profile table VO. */
	public ProfileTableVO() {
	}

	public ProfileTableVO(final ProfileTable dto) {
		id = dto.getId();
		profileId = dto.getProfileId();
		tableName = dto.getTableName();
		description = dto.getDescription();
	}

	/**
	 * Gets the fields.
	 *
	 * @return the fields
	 */
	public Set<ProfileTableField> getFields() {
		return fields;
	}

	/**
	 * Gets the filters.
	 *
	 * @return the filters
	 */
	public List<ProfileTableFieldFilter> getFilters() {
		return filters;
	}

	/**
	 * Sets the fields.
	 *
	 * @param field
	 *            the new fields
	 */
	public void setFields(final Set<ProfileTableField> field) {
		fields = field;
	}

	/**
	 * Sets the filters.
	 *
	 * @param filters
	 *            the new filters
	 */
	public void setFilters(final List<ProfileTableFieldFilter> filters) {
		this.filters = filters;
	}

	@Override
	public String toString() {
		return String.format("Table Data :" + "\n\t Table Name=%s"
				+ "\n\t Description=%s" + "\n\t Fields=%s" + "\n\t Filters=%s",
				tableName, description, fieldsListStr(), filterListStr());
	}

	private String fieldsListStr() {
		String str = "";
		if (this.fields != null) {
			for (ProfileTableField fld : this.fields) {
				str += "\n" + fld.toString();
			}
		} else {
			return "\n";
		}
		return str;
	}

	private String filterListStr() {
		String str = "";
		if (this.filters != null) {
			for (ProfileTableFieldFilter fltr : this.filters) {
				str += "\n" + fltr.toString();
			}
		} else {
			return "\n";
		}
		return str;
	}
}
