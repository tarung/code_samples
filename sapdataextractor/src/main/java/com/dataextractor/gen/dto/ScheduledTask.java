package com.dataextractor.gen.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.google.common.collect.ImmutableMap;

public class ScheduledTask {

	private Long id;

	private String name;

	private Date startDate;

	private Date endDate;

	private Long repeatAfter;

	private Date lastExecutionTime ;

	private Date nextExecutionTime ;

	private Long profileId;

	/** The field to column map. */
	public static final ImmutableMap<String, String> fieldMap;

	static {
		final HashMap<String, String> mp = new HashMap<String, String>();
		mp.put("id", "id");
		mp.put("name", "name");
		mp.put("profileId", "profile_id");
		mp.put("startDate", "start_date");
		mp.put("endDate", "end_date");
		mp.put("repeatAfter", "repeat_after");
		mp.put("lastExecutionTime", "last_execution_time");
		mp.put("nextExecutionTime", "next_execution_time");
		fieldMap = ImmutableMap.copyOf(mp);
	}


	/** The Date format. */
	private static DateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");


	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEndDateStr() {
		return dateStr(endDate);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getStartDateStr() {
		return dateStr(startDate);
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRepeatAfter() {
		return repeatAfter;
	}

	public String getRepeatAfterStr() {
		String result = "";
		long rept = repeatAfter;

		int days = (int)(rept/(24*60*60*1000));
		if (days>0){
			result += days + " days";
			rept = rept % (days*24*60*60*1000);
		}
		int hours = (int)(rept/(60*60*1000));
		if(hours > 0){
			if(!result.isEmpty())
				result += " ";
			result += hours + " hours";
			rept = rept % (hours*60*60*1000);
		}
		int mins = (int)(rept/(60*1000));
		if(mins > 0){
			if(!result.isEmpty())
				result += " ";
			result += mins + " mins";
		}
		return result;
	}

	public void setRepeatAfter(Long repeatAfter) {
		this.repeatAfter = repeatAfter;
	}

	public Date getLastExecutionTime() {
		return lastExecutionTime;
	}

	public String getLastExecutionTimeStr() {
		return this.dateStr(lastExecutionTime);
	}

	public void setLastExecutionTime(Date lastExecutionTime) {
		this.lastExecutionTime = lastExecutionTime;
	}

	public Date getNextExecutionTime() {
		return nextExecutionTime;
	}

	public String getNextExecutionTimeStr() {
		return this.dateStr(nextExecutionTime);
	}

	public void setNextExecutionTime(Date nextExecutionTime) {
		this.nextExecutionTime = nextExecutionTime;
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	private String dateStr(Date date) {
		if(date != null)
			return DATE_FORMAT.format(date);
		else
			return "";
	}

}
