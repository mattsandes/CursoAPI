package br.com.sandes.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sandes.exceptions.ResourceNotFoundException;
import br.com.sandes.model.Person;
import br.com.sandes.repositories.PersonRepository;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());
    
    @Autowired
    PersonRepository personRepository;

    public List<Person> findAll(){

        logger.info("Finding all person!");

        return personRepository.findAll();
    }

    public Person findById(Long id){
        logger.info("Finding one person!");

        return personRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
    }
    
    public Person create(Person person) {
    	logger.info("Person created!");
    	
    	
    	return personRepository.save(person);
    }
    
    public Person update(Person person) {
    	logger.info("Person updated!");
    	
    	var entity = personRepository
    			.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
    	
    	entity.setFirst_name(person.getFirst_name());
    	entity.setLast_name(person.getLast_name());
    	entity.setAddress(person.getAddress());
    	entity.setGender(person.getGender());
    	
    	return personRepository.save(entity);
    }
    
    public void delete(Long id) {
    	logger.info("Person deleted!");
    	
    	var entity = personRepository
    			.findById(id).orElseThrow(
    					() -> new ResourceNotFoundException("No records found for this id!"));
    	
    	personRepository.delete(entity);
    }
}
