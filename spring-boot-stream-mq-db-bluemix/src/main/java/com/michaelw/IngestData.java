package com.michaelw;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by michaelwang on 12/22/16.
 */
@Entity
public class IngestData {
	@Id
    @GeneratedValue
    private Long id;
	
	private String filePath;
	
	private String value;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "IngestData [filePath=" + filePath + ", value=" + value + "]";
	}
}
