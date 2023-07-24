package com.retooling.chicken.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ChickenValidationErrorException extends Exception {

	public ChickenValidationErrorException() {
		super();
	}
	
	public ChickenValidationErrorException(String message) {
		super(message);
	}
	
}
