package com.dataextractor.gen.dto;

import java.util.Date;

public class ExtractionLogRecord {

	private Long id ;

	private long taskId ;

	private Date timeStamp;

	private String tableName;

	private String message;

	boolean error;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isError() {
		return error;
	}

	public boolean getError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
