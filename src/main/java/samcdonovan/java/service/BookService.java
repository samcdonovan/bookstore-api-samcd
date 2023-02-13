package samcdonovan.java.service;

import org.springframework.web.bind.annotation.GetMapping;
import samcdonovan.java.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import samcdonovan.java.service.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/books")
    public List getAllBooks() {
        List books = new ArrayList();
        bookRepository.findAll().forEach(book -> books.add(book));
        return books;
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id).get();
    }

    public void saveOrUpdate(Book book) {
        bookRepository.save(book);
    }

    public void delete(int id) {
        bookRepository.deleteById(id);
    }
}