package br.com.sandes.exceptions.handler;

import br.com.sandes.exceptions.ExceptionReponse;
import br.com.sandes.exceptions.UnsuportedMathOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice //Serve para resposabilizar as excecoes para essa classe quando nao houver
                  //uma classe tratando o erro
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    //essa classe vai tratar as excecoes do tipo 500;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionReponse> handleAllExceptions(
            Exception ex, WebRequest request){
        ExceptionReponse exceptionReponse = new ExceptionReponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionReponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnsuportedMathOperationException.class)
    public final ResponseEntity<ExceptionReponse> handleBadRequestExceptions(
            Exception ex, WebRequest request){
        ExceptionReponse exceptionReponse = new ExceptionReponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionReponse, HttpStatus.BAD_REQUEST);
    }
}
