package samcdonovan.java.controller;

import samcdonovan.java.model.Book;
import samcdonovan.java.model.BookDAO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private BookDAO dao;

    public BookController(){
        this.dao = new BookDAO();
    }

    /**
     * GET path for books with a specific ID.
     *
     * @param id The database ID of the book
     * @return Book The book retrieved from the database
     */
    @GetMapping("/books")
    public Book getBook(@RequestParam Integer id) {
        return dao.getBookWithId(id);
    }

    /**
     * GET path for all books
     *
     * @return List A list containing all the books in the database
     */
    @GetMapping("/books")
    public List<Book> getBook() {
        return dao.getAllBooks();
    }
}
