package com.dataextractor.gen.dto;

import java.io.Serializable;

/** This class represents the primary key of the extraction_log table. */
@SuppressWarnings("all")
public class ExtractionLogRecordPk implements Serializable {
	protected Long id;

	public ExtractionLogRecordPk() {
	}

	public ExtractionLogRecordPk(final Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String toString() {
		final StringBuffer ret = new StringBuffer();
		ret.append("com.dataextractor.gen.dto.TableListPk: ");
		ret.append("id=" + id);
		return ret.toString();
	}

}
