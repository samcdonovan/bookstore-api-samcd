package samcdonovan.java.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper; // necessary otherwise 406 status code
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
     * @return ResponseEntity Containing the HTTP status code for the request.
     */
    @PostMapping("/books")
    public ResponseEntity<HttpStatus> addBook(@RequestBody Book book) {

        System.out.println("POST request at /books, body: " + book);
        try {
            dao.insert(book);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET path with optional parameters; if no parameters are passed,
     * it retrieves all books from the database, otherwise it performs a search
     * based on the parameters that were passed.
     *
     * @param title  The title to search for
     * @param author The author to search for
     * @param isbn   The ISBN to search for
     * @param price The price to search for
     * @return ResponseEntity containing the list of books from the database and
     * a HTTP status code for the request
     */
    @GetMapping(value = "/books")
    public ResponseEntity<List<Book>> getBooks(@RequestParam(required = false) String title,
                                               @RequestParam(required = false) String author,
                                               @RequestParam(required = false) String isbn,
                                               @RequestParam(required = false) Double price) {

        List<Book> bookList = new ArrayList<>();
        String message = "GET request at /books?";
        String priceString = null;

        /* if the respective param exists, append the param name to it */
        if (title != null) {
            title = "title:" + title;
            message += "title=" + title;
        }
        if (author != null) {
            author = "author:" + author;
            message += "author=" + author;
        }
        if (isbn != null) {
            isbn = "isbn:" + isbn;
            message += "isbn=" + isbn;
        }
        if (price != null) {
            priceString = "price:" + price;
            message += "price=" + price;
        }

        System.out.println(message); // print message to console
        try {

            /* if all parameters are null, retrieve all books from database */
            if (title == null && author == null && isbn == null && priceString == null) bookList = dao.getAll();
            else bookList = dao.get(title, author, isbn, priceString);

            /* return NO_CONTENT code if there are no books in db otherwise return OK code */
            if (bookList.size() == 0) return new ResponseEntity<>(bookList, HttpStatus.NO_CONTENT);
            else return new ResponseEntity<>(bookList, HttpStatus.OK);

        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET path for books with a specific ID
     *
     * @param id The database ID of the book
     * @return ResponseEntity containing the retrieved book and a HTTP status code for this request
     */
    @GetMapping("/books/{id}")
    public ResponseEntity<List<Book>> getBook(@PathVariable Integer id) {
        System.out.println("GET request at books/" + id);
        Book book = null;
        try {
            book = dao.get(id);
            List<Book> bookList = new ArrayList<>();
            if (book != null) {
                bookList.add(book);
                return new ResponseEntity<>(bookList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(bookList, HttpStatus.NOT_FOUND);
            }
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
     * @return ResponseEntity Containing a HTTP status code for the request
     */
    @PutMapping("/books/{id}")
    public ResponseEntity<HttpStatus> updateBookByPut(@RequestBody Book book, @PathVariable("id") Integer id) {
        System.out.println("PUT request at /books/" + id + ", body: " + book);
        try {
            dao.updateDocument(book, id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PATCH path for updating fields for a document in the database
     *
     * @param book The fields of the book to be updated
     * @param id   The ID of the book to be updated
     * @return ResponseEntity Containing a HTTP status code for the request
     */
    @PatchMapping("/books/{id}")
    public ResponseEntity<HttpStatus> updateBookByPatch(@RequestBody Book book, @PathVariable("id") Integer id) {
        System.out.println("PATCH request at /books/" + id + ", body: " + book);
        try {
            dao.updateFields(book, id);
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
     * @return ResponseEntity Containing a HTTP status code for the request
     */
    @DeleteMapping("/books/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") Integer id) {
        System.out.println("DELETE request at /books/" + id);
        try {
            boolean success = dao.delete(id);
            if (success) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * DELETE path for deleting all books in the table
     *
     * @return ResponseEntity containing a HTTP status code for the request
     */
    @DeleteMapping("/books")
    public ResponseEntity<HttpStatus> deleteAllBooks() {
        System.out.println("PUT request at /books");
        try {
            dao.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
