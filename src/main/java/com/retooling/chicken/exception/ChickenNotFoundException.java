package com.retooling.chicken.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChickenNotFoundException extends Exception {

	public ChickenNotFoundException() {
		super();
	}
	
	public ChickenNotFoundException(String message) {
		super(message);
	}
	
}
