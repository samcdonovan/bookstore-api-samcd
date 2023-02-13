package samcdonovan.java.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samcdonovan.java.model.Book;
import samcdonovan.java.service.BookDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Boot Rest Controller for handling and
 * retrieving data via REST paths (CRUD).
 */
@RestController
@RequestMapping("/")
public class BookController {

    BookDAO dao = new BookDAO();

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        System.out.println(book);
        try {
            Book newBook = dao.addBook(book);
            System.out.println(newBook);
            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET path for books with a specific ID.
     *
     * @param id The database ID of the book
     * @return Book The book retrieved from the database
     */
    @GetMapping(value = "/books", params = "id")
    public Book getBook(@RequestParam Integer id) {
        Book book = null;
        try {
            book = dao.findById(id);
        } catch (Exception exception) {
            System.out.println(HttpStatus.NO_CONTENT);
            System.out.println("Book with ID " + id + " does not exist.");
        }
        return book;
    }

    /**
     * GET path for all books
     *
     * @return List A list containing all the books in the database
     */
    @GetMapping("/books")
    public List<Book> getBooks() {
        List<Book> list = new ArrayList<>();
        try {
            list = dao.findAll();
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return list;
    }


}
