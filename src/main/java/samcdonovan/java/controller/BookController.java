package samcdonovan.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import samcdonovan.java.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import samcdonovan.java.service.BookRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookRepository repository;

    /**
     * GET path for books with a specific ID.
     *
     * @param id The database ID of the book
     * @return Book The book retrieved from the database
     */
   /* @GetMapping("/books/{id}")
    public Book getBook(@RequestParam Integer id) {
        Book book = null;
        try{
            book = dao.getBookWithId(id);
        } catch(Exception exception){
            System.out.println("Book with ID " + id + " does not exist.");
        }
        return book;
    }
*/

    /**
     * GET path for all books
     *
     * @return List A list containing all the books in the database
     */
    @GetMapping("/books")
    public List<Book> getBooks() {
        List<Book> list = new ArrayList<>();
        try {
            list = repository.findAll();
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return list;
    }
}
