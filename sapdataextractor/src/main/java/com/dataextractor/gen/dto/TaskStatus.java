package com.dataextractor.gen.dto;

/**
 * The Class TaskStatus.
 */
public class TaskStatus {

	/** The task id. */
	private Long taskId;

	/** The status. */
	private String status ;

	/** The percent completion. */
	private int percentCompletion;

	/**
	 * Gets the task id.
	 *
	 * @return the task id
	 */
	public Long getTaskId() {
		return taskId;
	}

	/**
	 * Sets the task id.
	 *
	 * @param takskId the task id
	 */
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
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
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the percent completion.
	 *
	 * @return the percent completion
	 */
	public int getPercentCompletion() {
		return percentCompletion;
	}

	/**
	 * Sets the percent completion.
	 *
	 * @param percentCompletion the new percent completion
	 */
	public void setPercentCompletion(int percentCompletion) {
		this.percentCompletion = percentCompletion;
	}

}
