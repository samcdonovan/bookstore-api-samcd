package samcdonovan.java.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Books
 */
public class BookDAO {

    private List<Book> bookList;

    public BookDAO(){
        bookList = new ArrayList<>();

        bookList.add(new Book("Test 1", "Test 1", "123123123123", 2.5));
        bookList.add(new Book("Test 2", "Test 2", "123123123124", 3.5));
        bookList.add(new Book("Test 3", "Test 3", "123123123125", 4.5));
        bookList.add(new Book("Test 4", "Test 4", "123123123126", 5.5));
    }

    /**
     * Retrieves a book with a specific ID
     *
     * @param id The ID of the book
     * @return Book The book from the database with the specified ID
     */
    public Book getBookWithId(int id){
        return bookList.get(0);
    }

    /**
     * Retrieves all books from the database
     *
     * @return List A list containing all the books
     */
    public List<Book> getAllBooks(){
        return this.bookList;
    }
}
