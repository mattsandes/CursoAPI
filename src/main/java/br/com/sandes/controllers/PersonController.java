package br.com.sandes;

import br.com.sandes.exceptions.UnsuportedMathOperationException;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class PersonController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}",
                    method = RequestMethod.GET)
    public Double sum(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

            if(!isNumeric(numberOne) || !isNumeric(numberTwo)){
                throw new UnsuportedMathOperationException("Please, set a numeric value");
            }

            return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }
}
