package br.com.sandes.controllers;

import br.com.sandes.model.Person;
import br.com.sandes.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//restControllers retornam os objetos e as informacoes dele
@RestController
@RequestMapping("/person")
public class PersonController {

    //Maniera nao tao eficiente de se fazer;

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findById (
            @PathVariable(value = "id") Long id) throws Exception {

        return personService.findById(id);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findingAll (){

        return personService.findAll();
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, //Produz um json como resposta;
            consumes = MediaType.APPLICATION_JSON_VALUE) //define que a requisicao sera feita via json;
    public void createPerson (
            @RequestBody Person person) throws Exception {

        personService.create(person);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE, //Produz um json como resposta;
            consumes = MediaType.APPLICATION_JSON_VALUE) //define que a requisicao sera feita via json;
    public Person update (
            @RequestBody Person person) throws Exception {

        return personService.update(person);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    public void delete (
            @PathVariable(value = "id") Long id){

        personService.delete(id);
    }
}