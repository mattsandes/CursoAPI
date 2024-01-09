package br.com.sandes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) //toda classe de erro precisa retornar um status code;
public class UnsuportedMathOperationException extends RuntimeException{

    public UnsuportedMathOperationException(String ex){
        super(ex);
    }
    private static final long serialVersionUID = 1L;
}
