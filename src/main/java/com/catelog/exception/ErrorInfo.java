package com.catelog.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorInfo {

	private List<String> errorMessage;
	private LocalDateTime time;

	public List<String> getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(List<String> errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
