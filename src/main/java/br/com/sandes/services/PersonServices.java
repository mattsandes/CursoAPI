package br.com.sandes.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
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

	@Autowired
	PagedResourcesAssembler<PersonVO> assembler;

    public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable){

        logger.info("Finding all person!");

		var personPage = personRepository.findAll(pageable);

		var personVosPage = personPage.map(
			p -> ModelMapper.parseObject(p, PersonVO.class));

		//retornando o link hateoas para cada item no banco de dados;
		personVosPage.map(
			p -> p.add(
				linkTo(methodOn(PersonController.class)
					.findById(p.getKey())).withSelfRel()));

		//retornando o link hateoas para a pagina que os itens estão;
		Link link = linkTo(methodOn(PersonController.class).findAll(
			pageable.getPageNumber(),
			pageable.getPageSize(),
			"asc")).withSelfRel();

		return assembler.toModel(personVosPage, link);
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
    	
    	entity.setFirstName(person.getFirstName());
    	entity.setLastName(person.getLastName());
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
