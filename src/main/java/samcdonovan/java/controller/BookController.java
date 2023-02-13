package samcdonovan.java.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samcdonovan.java.model.Book;
import samcdonovan.java.service.BookDAO;

import javax.sound.midi.SysexMessage;
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

    /**
     * POST path for creating new books in the database
     *
     * @param book The request body for a POST request containing information
     *             about the book to be inserted.
     * @return ResponseEntity A response entity containing the book object
     * and a HTTP status code.
     */
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
    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable Integer id) {
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

    /**
     * PUT path for updating the book with the specified ID
     *
     * @param book The request body for a PUT request containing information
     *             about the book to be updated.
     * @param id The ID of the book to be updated
     */
    @PutMapping("/books/{id}")
    public void updateBook(@RequestBody Book book, @PathVariable("id") Integer id) {
        try {
            dao.updateBook(book, id);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    /**
     * DELETE path for deleting the book with the specified ID
     *
     * @param id The ID of the book to be deleted
     */
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable("id") Integer id){
        try{
            dao.deleteBookById(id);
        } catch (Exception exception){
            System.out.println(exception);
        }
}


}
