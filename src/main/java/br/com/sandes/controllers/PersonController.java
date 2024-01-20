package br.com.sandes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.sandes.model.Person;
import br.com.sandes.services.PersonServices;

@RestController
@RequestMapping("/person")
public class PersonController {

    //Assim, o spring fica responsavel pela instancia;
    @Autowired
    PersonServices personServices;

    //solução nao eficaz
    // PersonServices personServices = new PersonServices();
    
    @RequestMapping(
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll(){
    	
    	return personServices.findAll();
    }

    @RequestMapping(value = "/{id}",
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findById(@PathVariable(value = "id") Long id) {

        return personServices.findById(id);
    }
    
    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Person create(@RequestBody Person person) {
    	return personServices.create(person);
    }
    
    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Person update(@RequestBody Person person) {
    	return personServices.update(person);
    }
    
    @RequestMapping(value = "/{id}",
    		method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id) {
    	personServices.delete(id);
    }

}