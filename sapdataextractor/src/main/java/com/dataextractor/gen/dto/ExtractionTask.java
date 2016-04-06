package com.dataextractor.gen.dto;

import java.io.Serializable;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/** The ExtractionTask Data object */
@SuppressWarnings("all")
public class ExtractionTask implements Serializable {

	/** This attribute maps to the column id in the extraction_history table. */
	protected Long id;

	/** The Profile Name. */
	protected String profileName;

	/** The Description. */
	protected String description;

	/** The status. */
	protected String status;

	/** The started on. */
	protected Date startedOn;

	/** The Completed on. */
	protected Date completedOn;

	/** The scheduler id. */
	protected Long schedulerId;

	/** The Date format. */
	private static DateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");

	static {
		final HashMap<String, String> mp = new HashMap<String, String>();
		mp.put("id", "id");
		fieldMap = ImmutableMap.copyOf(mp);
		STATUS_LIST = ImmutableList.copyOf(new String[]{"INPROGRESS", "COMPLETED", "ABORTED", "COMPLETE_WITH_ERRORS"});
	}

	public Long getSchedulerId() {
		return schedulerId;
	}

	public void setSchedulerId(Long schedulerId) {
		this.schedulerId = schedulerId;
	}

	/** Sets the Description. */
	public void setDescription(String description) {
		this.description = description;
	}

	/** The percent completion. */
	protected int percentCompletion;

	/** The field to column map. */
	public static final ImmutableMap<String, String> fieldMap;

	/** The Constant STATUS_LIST. */
	public static final ImmutableList<String> STATUS_LIST ;

	/**
	 * Creates the primary key object.
	 *
	 * @return the extraction task PK
	 */
	public ExtractionTaskPk createPk() {
		return new ExtractionTaskPk(id);
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Gets the profile name.
	 *
	 * @return the profile name
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * Sets the profile name.
	 *
	 * @param profileName the new profile name
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * Gets the description, A system generated
	 * String created from the parameters present in the profile object.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * Sets the status 'INPROGRESS' 'COMPLETED', or 'CANCELLED'.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		if(!STATUS_LIST.contains(status))
			throw new IllegalArgumentException(status + " is not a valid status");
		this.status = status;
	}

	/**
	 * Gets the started on date and time 'INPROGRESS' 'COMPLETED', or 'CANCELLED'
	 *
	 * @return the started on
	 */
	public Date getStartedOn() {
		return startedOn;
	}

	/**
	 * Sets the started on date and time.
	 *
	 * @param startedOn the new started on
	 */
	public void setStartedOn(Date startedOn) {
		this.startedOn = startedOn;
	}

	/** Gets the 'started on' date in formatted String. */
	public String getStartedOnStr() {
		return DATE_FORMAT.format(startedOn);
	}

	/** Gets the 'completed on' date in formatted String. */
	public String getCompletedOnStr() {
		if(this.completedOn != null )
			return DATE_FORMAT.format(this.completedOn);
		else
			return "";
	}

	/**
	 * Gets the completed on date and time.
	 *
	 * @return the completed on
	 */
	public Date getCompletedOn() {
		return completedOn;
	}

	/**
	 * Sets the completed on date and time
	 *
	 * @param completedOn the new completed on
	 */
	public void setCompletedOn(Date completedOn) {
		this.completedOn = completedOn;
	}

	/**
	 * Gets the percent completion status.
	 *
	 * @return the percent completion status.
	 */
	public int getPercentCompletion() {
		return percentCompletion;
	}

	/**
	 * Sets the percent completion status.
	 *
	 * @param percentCompletion the new percent completion status.
	 */
	public void setPercentCompletion(int percentCompletion) {
		this.percentCompletion = percentCompletion;
	}

}
