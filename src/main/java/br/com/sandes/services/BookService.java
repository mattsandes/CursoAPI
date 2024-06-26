package br.com.sandes.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import br.com.sandes.controllers.PersonController;
import br.com.sandes.data.vo.v1.PersonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.sandes.controllers.BookController;
import br.com.sandes.data.vo.v1.BooksVO;
import br.com.sandes.exceptions.ResourceNotFoundException;
import br.com.sandes.mapper.ModelMapper;
import br.com.sandes.model.Books;
import br.com.sandes.repositories.BookRepository;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    PagedResourcesAssembler<BooksVO> assembler;

    private static final Logger logger = Logger.getLogger(BookService.class.getName());

    public PagedModel<EntityModel<BooksVO>> findAll(Pageable pageable){

        logger.info("Finding all books");

        var bookPage = bookRepository.findAll(pageable);

        var booksVosPage = bookPage.map(
                p -> ModelMapper.parseObject(p, BooksVO.class));

        booksVosPage.map(
                p -> p.add(
                    linkTo(methodOn(BookController.class)
                        .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class).findAll(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                "asc")).withSelfRel();

        return assembler.toModel(booksVosPage, link);
    }

    public BooksVO findById(Long id){
        logger.info("Finding a book");

        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No resource found for this id"));

        var vo = ModelMapper.parseObject(entity, BooksVO.class);

        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());

        return vo;
    }

    public BooksVO create(BooksVO booksVO){
        logger.info("Book created");

        var book = ModelMapper.parseObject(booksVO, Books.class);

        var vo = ModelMapper.parseObject(bookRepository.save(book), BooksVO.class);

        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public BooksVO update(BooksVO booksVO){
        logger.info("Book Updated");

        var entity = bookRepository.findById(booksVO.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        entity.setTitle(booksVO.getTitle());
        entity.setPrice(booksVO.getPrice());
        entity.setAuthor(booksVO.getAuthor());
        entity.setLaunchDate(booksVO.getLaunchDate());

        var vo = ModelMapper.parseObject(bookRepository.save(entity), BooksVO.class);

        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(Long id){
        logger.info("Book deleted");

        var entity = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        bookRepository.delete(entity);
    }
}
