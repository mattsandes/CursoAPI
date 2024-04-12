package br.com.sandes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectNullExceptions extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RequiredObjectNullExceptions(String message) {
		super(message);
	}
	
	public RequiredObjectNullExceptions() {
		super("It's not allowed to persist a null object!");
	}

}
