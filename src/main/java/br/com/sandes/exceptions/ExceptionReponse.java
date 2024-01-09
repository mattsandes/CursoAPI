package br.com.sandes.exceptions;

import java.io.Serializable;
import java.util.Date;

public class ExceptionReponse implements Serializable {
    //Porque estamos implementando o serializable:
    //mais facil mandar para cache e para trafego de rede.

    private static final long serialVersionUID = 1L;

    //Essa classe ira retornar um json com esses tres campos abaixo;
    private Date timestamp;
    private String message;
    private String details;

    public ExceptionReponse(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
