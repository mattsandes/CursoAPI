package br.com.sandes.unittests.mapper.mocks;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.sandes.data.vo.v1.BooksVO;
import br.com.sandes.model.Books;

public class MockBook {
        public Books mockEntity() {
            return mockEntity(0);
        }

        public BooksVO mockVO() {
            return mockVO(0);
        }

        public List<Books> mockEntityList() {
            List<Books> books = new ArrayList<Books>();
            for (int i = 0; i < 14; i++) {
            	books.add(mockEntity(i));
            }
            return books;
        }

        public List<BooksVO> mockVOList() {
            List<BooksVO> books = new ArrayList<>();
            for (int i = 0; i < 14; i++) {
            	books.add(mockVO(i));
            }
            return books;
        }

        public Books mockEntity(Integer number) {
        	Date data = new Date(number);
        	
            Books book = new Books();
            
            book.setId(number.longValue());
            book.setTitle("Book Title Test " + number);
            book.setAuthor("Author Name Test " + number);
            book.setPrice(Double.valueOf(number));
            book.setLaunchDate(data);

            return book;
        }

        public BooksVO mockVO(Integer number) {
        	Date data = new Date(number);
        	
        	BooksVO book = new BooksVO();

        	book.setKey(number.longValue());
            book.setTitle("Book Title Test " + number);
            book.setAuthor("Author Name Test " + number);
            book.setPrice(Double.valueOf(number));
            book.setLaunchDate(data);

            return book;
        }
}
