package br.com.sandes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.sandes.data.vo.v1.PersonVO;
import br.com.sandes.model.Person;
import br.com.sandes.repositories.PersonRepository;
import br.com.sandes.unittests.mapper.mocks.MockPerson;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices service;

    @Mock
    PersonRepository personRepository;

    @BeforeEach
    void setUpMocks() throws Exception{
        input = new MockPerson();
    }
    
    @Test
    void findAll() {
    	List<Person> list = input.mockEntityList();
    	
    	when(personRepository.findAll()).thenReturn(list);
    	
    	var people = service.findAll();
    	
    	assertNotNull(people);
    	assertEquals(14, people.size());
    	
    	var personOne = people.get(1);
   
    	assertTrue(personOne.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
    	assertEquals("Addres Test1", personOne.getAddress());
    	assertEquals("First Name Test1", personOne.getFirst_name());
    	assertEquals("Last Name Test1", personOne.getLast_name());
    	assertEquals("Female", personOne.getGender());
    }

    @Test
    void findById() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));

        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirst_name());
        assertEquals("Last Name Test1", result.getLast_name());
        assertEquals("Female", result.getGender());
    }

    @Test
    void create() {
        Person entity = input.mockEntity(1);

        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(personRepository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));

        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirst_name());
        assertEquals("Last Name Test1", result.getLast_name());
        assertEquals("Female", result.getGender());
    }

    @Test
    void update() {
        Person entity = input.mockEntity(1);

        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(personRepository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));

        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirst_name());
        assertEquals("Last Name Test1", result.getLast_name());
        assertEquals("Female", result.getGender());
    }

    @Test
    void delete() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }
}