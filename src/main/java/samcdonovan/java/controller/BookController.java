package samcdonovan.java.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import samcdonovan.java.model.Book;

@RestController
public class BookController {

    @GetMapping("/books")
    public Book getBook() {

    }
}
