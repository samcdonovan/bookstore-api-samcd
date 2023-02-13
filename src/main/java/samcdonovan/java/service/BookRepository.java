package samcdonovan.java.service;

import samcdonovan.java.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BookRepository extends JpaRepository <Book, Integer> {
    List<Book> findByTitle(String title);

    List<Book> findByIsbn(String isbn);

}