package com.catelog.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorInfo> invalidException(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getFieldErrors().stream().map(e -> e.getField() + ":" + e.getDefaultMessage())
				.collect(Collectors.toList());
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(errors);
		errorInfo.setTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<String> handleException(ProductException productException) {
		return new ResponseEntity<String>(productException.getErrorMessage(),
				productException.getHttpStatus());
	}

	


}
