package br.com.sandes.exceptions;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

//criada a entidade de erro que sera jogada quando um erro ocorrer;
public class ExceptionResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Date timeStamp;
    private String message;
    private String details;

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public ExceptionResponse(Date timeStamp, String message, String details) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }
}
