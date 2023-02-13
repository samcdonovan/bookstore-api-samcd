package samcdonovan.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import samcdonovan.java.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import samcdonovan.java.model.BookDAO;
import samcdonovan.java.service.BookRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Boot Rest Controller for handling and
 * retrieving data via REST paths (CRUD).
 */
@RestController
@RequestMapping("/")
public class BookController {

    @Autowired
    BookRepository repository;

    BookDAO dao = new BookDAO();

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
            System.out.println(id);
            book = dao.findById(id);
        } catch (Exception exception) {

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
