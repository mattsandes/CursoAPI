package br.com.sandes.mapper;

import br.com.sandes.data.vo.v1.BooksVO;
import br.com.sandes.data.vo.v1.PersonVO;
import br.com.sandes.model.Books;
import br.com.sandes.model.Person;

import java.util.ArrayList;
import java.util.List;

public class ModelMapper {

    private static final org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();

    static {
        mapper.createTypeMap(
                Person.class, PersonVO.class)
                .addMapping(Person::getId, PersonVO::setKey);

        mapper.createTypeMap(
                PersonVO.class, Person.class)
                .addMapping(PersonVO::getKey, Person::setId);

        //criando mapping para o key e id de books;

        mapper.createTypeMap(
                Books.class, BooksVO.class).addMapping(Books::getId, BooksVO::setKey);

        mapper.createTypeMap(
                BooksVO.class, Books.class)
                .addMapping(BooksVO::getKey, Books::setId);
    }

    public static <O, D> D parseObject(O origin, Class<D> destination){
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination){

        List<D> destinationObject = new ArrayList<D>();

        for(O o: origin){
            destinationObject.add(mapper.map(o, destination));
        }

        return destinationObject;
    }
}
