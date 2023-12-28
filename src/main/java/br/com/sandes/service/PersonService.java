package br.com.sandes.service;

import br.com.sandes.model.Person;
import br.com.sandes.repositories.PersonRepository;
import br.com.sandes.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    //isso sera jogado no log do spring boo quando a applicacao estiver rodando;
    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    public Person findById(Long id){

        logger.info("Finding one person!");

        Person person = new Person();

        person.setNome(person.getNome());
        person.setSobrenome(person.getSobrenome());
        person.setEndereco(person.getEndereco());
        person.setSexo(person.getSexo());

        return personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this id!")
        );

    }

    public List<Person> findAll(){

        logger.info("Finding all people person!");

        return personRepository.findAll();
    }

    //Criando as outras operacoes do rest (POST, DELETE, PUT)

    public Person create (Person person){

        logger.info("Person created!");

        return personRepository.save(person);
    }

    public Person update(Person person){

        logger.info("Person updated");

        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));

        entity.setNome(person.getNome());
        entity.setSobrenome(person.getSobrenome());
        entity.setEndereco(person.getEndereco());
        entity.setSexo(person.getSexo());

        return personRepository.save(entity);
    }

    public void delete(Long id){
        logger.info("Person deleted!");

        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));

        personRepository.delete(entity);
    }
}
