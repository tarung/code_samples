package com.dataextractor.model;

import java.util.List;

import com.dataextractor.gen.dto.ExtractionProfile;

public class ExtractionProfileVO extends ExtractionProfile {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private List<ProfileTableVO> tables;

	/** The DB connection name. */
	private String dbConnectionName;

	/** The sap system name. */
	private String sapSystemName;

	public ExtractionProfileVO() {

	}

	public ExtractionProfileVO(final ExtractionProfile dto) {
		id = dto.getId();
		profileDescription = dto.getProfileDescription();
		profileName = dto.getProfileName();
		dbConnectionId = dto.getDbConnectionId();
		sapSystemId = dto.getSapSystemId();
		continueOnFailure = dto.isContinueOnFailure();
		ifTableExists = dto.getIfTableExists();
		writeBatchSize = dto.getWriteBatchSize();
	}

	/**
	 * @return the dbConnectionName
	 */
	public String getDbConnectionName() {
		return dbConnectionName;
	}

	/**
	 * @return the sapSystemName
	 */
	public String getSapSystemName() {
		return sapSystemName;
	}

	/**
	 * @return the tables
	 */
	public List<ProfileTableVO> getTables() {
		return tables;
	}

	/**
	 * @param dbConnectionName
	 *            the dbConnectionName to set
	 */
	public void setDbConnectionName(final String dbConnectionName) {
		this.dbConnectionName = dbConnectionName;
	}

	/**
	 * @param sapSystemName
	 *            the sapSystemName to set
	 */
	public void setSapSystemName(final String sapSystemName) {
		this.sapSystemName = sapSystemName;
	}

	/**
	 * @param tables
	 *            the tables to set
	 */
	public void setTables(final List<ProfileTableVO> tables) {
		this.tables = tables;
	}

	@Override
	public String toString() {

		return String.format("" + "Extraction Profile Details "
				+ "\n\t Profile Name=%s " + "\n\t Profile Description=%s "
				+ "\n\t DB Connection Name=%s " + "\n\t SAP System Name=%s "
				+ "\n\t Tables : %s", profileName, profileDescription,
				dbConnectionName, sapSystemName, tableListStr());
	}

	private String tableListStr() {
		String str = "";
		if (tables != null) {
			for (ProfileTableVO tbl : this.tables) {
				str += "\n" + tbl.toString();
			}
		} else {
			return "\n";
		}
		return str;
	}

}
