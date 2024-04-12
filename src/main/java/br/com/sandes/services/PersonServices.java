package br.com.sandes.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sandes.controllers.PersonController;
import br.com.sandes.data.vo.v1.PersonVO;
import br.com.sandes.exceptions.RequiredObjectNullExceptions;
import br.com.sandes.exceptions.ResourceNotFoundException;
import br.com.sandes.mapper.ModelMapper;
import br.com.sandes.model.Person;
import br.com.sandes.repositories.PersonRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository personRepository;

    public List<PersonVO> findAll(){

        logger.info("Finding all person!");

        var persons = ModelMapper.parseListObjects(personRepository.findAll(), PersonVO.class);

		persons.stream()
				.forEach(p -> p.add(linkTo(methodOn(PersonController.class)
						.findById(p.getKey())).withSelfRel()));

		return persons;
    }

    public PersonVO findById(Long id){
        logger.info("Finding one person!");

        var entity = personRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
        
        var vo = ModelMapper.parseObject(entity, PersonVO.class);

		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

		return vo;
    }
    
    public PersonVO create(PersonVO person) {
    	if(person == null) {
    		throw new RequiredObjectNullExceptions();
    	}
    	
		logger.info("Person created!");

		var entity = ModelMapper.parseObject(person, Person.class);

		var vo = ModelMapper.parseObject(personRepository.save(entity), PersonVO.class);

		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

		return vo;
	}
    
    public PersonVO update(PersonVO person) {
    	if(person == null) {
    		throw new RequiredObjectNullExceptions();
    	}
    	
    	logger.info("Person updated!");
    	
    	var entity = personRepository
    			.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
    	
    	entity.setFirst_name(person.getFirst_name());
    	entity.setLast_name(person.getLast_name());
    	entity.setAddress(person.getAddress());
    	entity.setGender(person.getGender());

    	var vo = ModelMapper.parseObject(personRepository.save(entity), PersonVO.class);

		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

		return vo;
    }
    
    @Transactional
    public PersonVO disablePerson(Long id){
    	logger.info("Disabling one person!");
    	
    	personRepository.disablePerson(id);
    	
    	var entity = personRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
    	
    	var vo = ModelMapper.parseObject(entity, PersonVO.class);
    	
    	vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
    	
    	return vo;
    }

    
    public void delete(Long id) {
    	logger.info("Person deleted!");
    	
    	var entity = personRepository
    			.findById(id).orElseThrow(
    					() -> new ResourceNotFoundException("No records found for this id!"));
    	
    	personRepository.delete(entity);
    }
}
