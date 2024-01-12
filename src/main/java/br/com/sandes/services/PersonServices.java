package br.com.sandes.services;

import br.com.sandes.model.Person;
import org.springframework.expression.spel.ast.Literal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public List<Person> findAll(){

        logger.info("Finding all person!");
        List<Person> personList = new ArrayList<>();

        for(int i = 0; i < 8; i++){
            Person person = mockPerson(i);

            personList.add(person);
        }

        return personList;
    }

    private Person mockPerson(int i) {
        Person person = new Person();

        person.setId(counter.incrementAndGet());
        person.setFirst_name("Person name: " + i);
        person.setLast_name("Person Last Name: " + i);
        person.setAddress("Person Address: " + i);
        person.setGender("Person gender: " + i);

        return person;
    }

    public Person findById(String id){
        logger.info("Finding one person!");

        Person person = new Person();

        person.setId(counter.incrementAndGet());
        person.setFirst_name("Mateus");
        person.setLast_name("Sandes");
        person.setAddress("Rua dos noia, 157 - Areias");
        person.setGender("Masculino");

        return person;
    }
}
