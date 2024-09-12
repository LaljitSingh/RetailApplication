package com.rewards.exception;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleCustomerNotFoundException(CustomerNotFoundException exception) {
		Map<String, Object> response = new HashMap<>();
		response.put("error", "Customer Not Found");
		response.put("message", exception.getMessage());
		response.put("status", HttpStatus.NOT_FOUND.value());
		logger.error(exception.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGeneralException(Exception exception) {
		Map<String, Object> response = new HashMap<>();
		response.put("error", "Internal Server Error");
		response.put("message", exception.getMessage());
		response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		logger.error(exception.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
