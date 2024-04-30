package br.com.sandes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.sandes.data.vo.v1.BooksVO;
import br.com.sandes.model.Books;
import br.com.sandes.repositories.BookRepository;
import br.com.sandes.unittests.mapper.mocks.MockBook;

class BookServiceTest {
	
	MockBook mockBook;
	
	@InjectMocks
	private BookService service;
	
	@Mock
	private BookRepository repository;
	
	@BeforeEach
	void setUp() throws Exception{
		MockitoAnnotations.openMocks(this);
		mockBook = new MockBook();
	}
	
	@Test
	@DisplayName("Validate that is possible to find one record from books")
	void findById() {
		Books entity = mockBook.mockEntity(1);
		
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertEquals("Book Title Test 1", result.getTitle());
		assertEquals("Author Name Test 1", result.getAuthor());
		assertEquals(1.0, result.getPrice());
		assertNotNull(result.getLaunchDate());
		assertTrue(result.toString().contains("</api/books/v1/1>;rel=\"self\""));
	}
	
	@Test
	@DisplayName("Validate that is possible to create a record for Book")
    void create() {
        Books entity = mockBook.mockEntity(1);

        Books persisted = entity;
        persisted.setId(1L);

        BooksVO vo = mockBook.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);
        
        assertNotNull(result);
		assertEquals("Book Title Test 1", result.getTitle());
		assertEquals("Author Name Test 1", result.getAuthor());
		assertEquals(1.0, result.getPrice());
		assertNotNull(result.getLaunchDate());
        assertTrue(result.toString().contains("</api/books/v1/1>;rel=\"self\""));
    }
	
	@Test
	@DisplayName("Validate that is possible to delete a record for books")
	void delete() {
		Books entity = mockBook.mockEntity(1);
		
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}
	
	@Test
	@DisplayName("Validate that is possible to update a record for books")
	void update() {
		Books entity = mockBook.mockEntity(1);
		
		Books saved = entity;
		saved.setId(1L);
		
		BooksVO vo = mockBook.mockVO(1);
		vo.setKey(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(saved);
		
		var result = service.update(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("</api/books/v1/1>;rel=\"self\""));
        assertNotNull(result);
		assertEquals("Book Title Test 1", result.getTitle());
		assertEquals("Author Name Test 1", result.getAuthor());
		assertEquals(1.0, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}
}
