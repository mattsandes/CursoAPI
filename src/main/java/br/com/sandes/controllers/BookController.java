package br.com.sandes.controllers;

import br.com.sandes.data.vo.v1.BooksVO;
import br.com.sandes.services.BookService;
import br.com.sandes.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/books/v1/")
@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping(produces = {MediaType.APPLICATION_YML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BooksVO> findAll(){
        return bookService.findAll();
    }

    @GetMapping(value = "/{id}",
                produces = {MediaType.APPLICATION_YML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public BooksVO findById(@PathVariable(value = "id") Long id){
        return bookService.findById(id);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
                consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public BooksVO create(@RequestBody BooksVO booksVO){
        return bookService.create(booksVO);
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
                consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public BooksVO update(@RequestBody BooksVO books){
        return bookService.update(books);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        bookService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
