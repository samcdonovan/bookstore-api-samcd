package samcdonovan.java.controller;

import samcdonovan.java.model.Book;
import samcdonovan.java.model.BookDAO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private BookDAO dao;

    @GetMapping("/books")
    public Book getBook(@RequestParam Integer id) {
        return new Book();
    }
}
