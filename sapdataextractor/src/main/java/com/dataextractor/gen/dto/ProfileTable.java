package com.dataextractor.gen.dto;

import java.io.Serializable;
import java.util.HashMap;

import com.google.common.collect.ImmutableMap;

/** The Profile Table. */
@SuppressWarnings("all")
public class ProfileTable implements Serializable {

	/** This attribute maps to the column id in the profile_table table. */
	protected Long id;

	/** This attribute maps to the column table_name in the profile_table table. */
	protected String tableName;

	/**
	 * This attribute maps to the column description in the profile_table table.
	 */
	protected String description;

	/** This attribute maps to the column profile_id in the profile_table table. */
	protected Long profileId;

	/** The field to column map. */
	public static final ImmutableMap<String, String> fieldMap;

	static {
		final HashMap<String, String> mp = new HashMap<String, String>();
		mp.put("id", "id");
		mp.put("tableName", "table_name");
		mp.put("profileId", "profile_id");
		fieldMap = ImmutableMap.copyOf(mp);
	}

	/**
	 * Method 'createPk'
	 *
	 * @return ProfileTablePk
	 */
	public ProfileTablePk createPk() {
		return new ProfileTablePk(id);
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
	 * Method 'getProfileId'
	 *
	 * @return Long
	 */
	public Long getProfileId() {
		return profileId;
	}

	/**
	 * Method 'getTableName'
	 *
	 * @return String
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
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
	 * Method 'setProfileId'
	 *
	 * @param profileId
	 */
	public void setProfileId(final Long profileId) {
		this.profileId = profileId;
	}

	/**
	 * Method 'setTableName'
	 *
	 * @param tableName
	 */
	public void setTableName(final String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "ProfileTable [id=" + id + ", tableName=" + tableName
				+ ", description=" + description + ", profileId=" + profileId
				+ "]";
	}

}
