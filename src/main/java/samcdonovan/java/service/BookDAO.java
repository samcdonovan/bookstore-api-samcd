package samcdonovan.java.service;

import samcdonovan.java.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Books
 */
public class BookDAO {

    public BookDAO(){    }

    /**
     * Retrieves a book with a specific ID
     *
     * @param id The ID of the book
     * @return Book The book from the database with the specified ID
     */
    public Book findById(int id) throws SQLException {

        String jdbcURL = "jdbc:h2:mem:bookstoredb";
        String username = "sa";
        String password = "";

        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Connected to H2 in server mode.");

        String sql = "SELECT * FROM books WHERE id=" + id;

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);

        Book book = new Book();

        while(resultSet.next()){
            book.setId(resultSet.getInt("id"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(resultSet.getString("author"));
            book.setIsbn(resultSet.getString("isbn"));
            book.setPrice(resultSet.getDouble("price"));
        }

        connection.close();

        return book;
    }

    /**
     * Retrieves all books from the database
     *
     * @return List A list containing all the books
     */
    public List<Book> findAll() throws SQLException{
        String jdbcURL = "jdbc:h2:mem:bookstoredb";
        String username = "sa";
        String password = "";

        Connection connection = DriverManager.getConnection(jdbcURL, username, password);

        String sql = "SELECT * FROM books";

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);

        Book book = new Book();
        List<Book> bookList = new ArrayList<>();
        while(resultSet.next()){
            book = new Book();
            book.setId(resultSet.getInt("id"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(resultSet.getString("author"));
            book.setIsbn(resultSet.getString("isbn"));
            book.setPrice(resultSet.getDouble("price"));
            bookList.add(book);
        }

        connection.close();
        return bookList;
    }
}
