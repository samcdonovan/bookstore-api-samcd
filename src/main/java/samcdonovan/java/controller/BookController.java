package samcdonovan.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper; // necessary otherwise 406 status code
import samcdonovan.java.model.Book;
import samcdonovan.java.service.BookDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        try {
            dao.insert(book);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET path for all books
     *
     * @return List A list containing all the books in the database
     */
    @GetMapping(value = "/books")
    public ResponseEntity<List<Book>> getBooks(
    @RequestParam(required = false) String title, @RequestParam(required = false) String author) {
        List<Book> bookList = new ArrayList<>();

        if (title == null && author == null){
            try {
                bookList = dao.getAll();
                if (bookList.size() > 0) {
                    return ResponseEntity.ok(bookList);
                } else {
                    return new ResponseEntity<>(bookList, HttpStatus.NO_CONTENT);
                }
            } catch (Exception exception) {
                System.out.println(exception);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if(title != null) title = "title:" + title;
        if(author != null) author = "author:" + author;

        try {
            System.out.println(title + "  " + author);
            bookList = dao.get(title, author);
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
            book = dao.get(id);
            if (book != null) {
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
     * @return ResponseEntity Containing a list of all books by the author and a HTTP status code for the request
     */
   /* @GetMapping(value = "/books")
    @ResponseBody
    public ResponseEntity<List<Book>> getBooksByAuthor
    (@RequestParam(required = false) String title, @RequestParam(required = true) String author) {

        System.out.println(title + "  " + author);
        if (title.isEmpty() && author.isEmpty()) return getBooks();

        List<Book> bookList = new ArrayList<>();

        if(!title.isEmpty()) title = "title:" + title;
        if(!author.isEmpty()) author = "author:" + author;

        System.out.println(title + "  " + author);
        try {
            //bookList = dao.get("title:" + title, "author:" + author);
            System.out.println(title + "  " + author);
            bookList = dao.get(title, author);
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/

    /**
     * GET path for books with a specific title
     *
     * @param title The author to search for
     * @return ResponseEntity Containing a list of all books containing the
     * given title and a HTTP status code for the request
     */
   /*@GetMapping(value = "/books", params = "title")
    public ResponseEntity<List<Book>> getBooksByTitle(@RequestParam String title) {
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = dao.get("title:" + title);
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    /*@GetMapping(value = "/books", params = "author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam String author) {
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = dao.get("author:" + author);
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    */


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
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Integer id) {
        try {
            boolean success = dao.delete(id);
            if (success) {
                return new ResponseEntity<>(null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * DELETE path for deleting all books in the table
     *
     * @return ResponseEntity containing a HTTP status code for the request
     */
    @DeleteMapping("/books")
    public ResponseEntity<HttpStatus> deleteAllBooks() {
        try {
            dao.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
