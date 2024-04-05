package br.com.sandes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sandes.data.vo.v1.security.AccountCredentialsVO;
import br.com.sandes.services.AuthServices;
import br.com.sandes.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthServices authServices;
	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Authenticates a user and returns a token")
	@PostMapping(value = "/signin",
				 produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
                 consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
		if(checkIfParamsIsNotNull(data)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body("Invalid client request");
		}
		
		var token = authServices.signin(data);
		
		if (token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body("Invalid client request");
		}
		
		return ResponseEntity.ok(token);
	}

	private boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
		return data == null ||
				data.getUsername() == null ||
				data.getUsername().isBlank() ||
				data.getPassword() == null ||
				data.getPassword().isBlank();
	}
}
