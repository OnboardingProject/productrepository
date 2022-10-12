package com.catelog.exception;

import org.springframework.http.HttpStatus;

public class ProductException extends RuntimeException{

	private final String errorMessage;
	private final HttpStatus httpStatus;
	
	public ProductException(String errorMessage, HttpStatus httpStatus) {
		super();
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}


}
