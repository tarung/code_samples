package com.dataextractor.gen.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/** The Extraction Profile. */
@SuppressWarnings("all")
public class ExtractionProfile implements Serializable {

    /** This attribute maps to the column id in the extraction_profile table.*/
    protected Long id;

    /** This attribute maps to the column profile_name in the extraction_profile table.*/
    protected String profileName;

    /** This attribute maps to the column profile_description in the extraction_profile table.*/
    protected String profileDescription;

    /** This attribute maps to the db_connection_id column in the extraction_profile table.*/
    protected Long dbConnectionId;

    /** This attribute maps to the sap_system_id column in the extraction_profile table.*/
    protected Long sapSystemId;

	/** This attribute maps to the column continue_on_failure in the extraction_profile table.*/
	protected boolean continueOnFailure;

	/** This attribute maps to the column write_batch_size in the extraction_profile table.*/
	protected Integer writeBatchSize;

	/** This attribute maps to the column delete_tables_if_exists in the extraction_profile table.*/
	protected String ifTableExists;

	/** The field to column map. */
	public static final ImmutableMap<String, String> fieldMap;

	private static List<String> TABLE_EXISTS_CONDITIONS ;


	static {
	    final HashMap<String, String> mp = new HashMap<String, String>();
	    mp.put("id", "id");
	    mp.put("profileName", "profile_name");
	    mp.put("profileDescription", "profile_description");
	    mp.put("dbConnectionId", "db_connection_id");
	    mp.put("sapSystemId", "sap_system_id");
	    mp.put("continueOnFailure", "continue_on_failure");
	    mp.put("writeBatchSize", "write_batch_size");
	    mp.put("ifTableExists", "if_table_exists");
	    fieldMap = ImmutableMap.copyOf(mp);

	    TABLE_EXISTS_CONDITIONS = ImmutableList.copyOf(new String[]{"DROP", "ABORT", "APPEND", "SKIP"});

	}

	public String getIfTableExists() {
		return ifTableExists;
	}

	public void setIfTableExists(String ifTableExists) {
		if(!TABLE_EXISTS_CONDITIONS.contains(ifTableExists)){
			throw new IllegalArgumentException("'ifTableExists' value must be in: "
					+ TABLE_EXISTS_CONDITIONS);
		}
		this.ifTableExists = ifTableExists;
	}

	public boolean isContinueOnFailure() {
		return continueOnFailure;
	}

	public void setContinueOnFailure(boolean continueOnFailure) {
		this.continueOnFailure = continueOnFailure;
	}

	public Integer getWriteBatchSize() {
		return writeBatchSize;
	}

	public void setWriteBatchSize(Integer writeBatchSize) {
		this.writeBatchSize = writeBatchSize;
	}

	public ExtractionProfilePk createPk() {
        return new ExtractionProfilePk(id);
    }

    public Long getDbConnectionId() {
        return dbConnectionId;
    }

    public Long getId() {
        return id;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public String getProfileName() {
        return profileName;
    }

    /** @return the sapSystemId */
    public Long getSapSystemId() {
        return sapSystemId;
    }

    /** @param dbConnectionId the dbConnectionId to set */
    public void setDbConnectionId(final Long dbConnectionId) {
        this.dbConnectionId = dbConnectionId;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setProfileDescription(final String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public void setProfileName(final String profileName) {
        this.profileName = profileName;
    }

    /** @param sapSystemId the sapSystemId to set */
    public void setSapSystemId(final Long sapSystemId) {
        this.sapSystemId = sapSystemId;
    }

}
