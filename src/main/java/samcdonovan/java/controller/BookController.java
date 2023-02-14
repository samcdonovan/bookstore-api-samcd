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

        try {
            Book newBook = dao.insertBook(book);
            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET path for all books
     *
     * @return List A list containing all the books in the database
     */
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = dao.findAll();
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET path for books with a specific ID
     *
     * @param id The database ID of the book
     * @return Book The book retrieved from the database
     */
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Integer id) {
        Book book = null;
        try {
            book = dao.findById(id);
            if(book != null) {
                return new ResponseEntity<>(book, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET path for books by a specific author
     *
     * @param author The author to search for
     * @return List A list containing all books by the author
     */
    @GetMapping(value = "/books", params = "author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam String author) {
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = dao.findByAuthor(author);
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT path for updating the book with the specified ID
     *
     * @param book The request body for a PUT request containing information
     *             about the book to be updated.
     * @param id   The ID of the book to be updated
     */
    @PutMapping("/books/{id}")
    public ResponseEntity<HttpStatus> updateBook(@RequestBody Book book, @PathVariable("id") Integer id) {
        try {
            dao.updateBook(book, id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * DELETE path for deleting the book with the specified ID
     *
     * @param id The ID of the book to be deleted
     */
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Integer id) {
        try {
            boolean success = dao.deleteBookById(id);
            if(success) {
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
