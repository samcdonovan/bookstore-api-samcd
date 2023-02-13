package samcdonovan.java.service;

import samcdonovan.java.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Books
 */
public class BookDAO {

    private Connection connection; // SQL connection variable

    /**
     * Empty constructor
     */
    public BookDAO() {
    }

    /**
     * Helper function to set up a H2 connection and then
     * run the specified query.
     *
     * @param query SQL query to run through H2
     * @return ResultSet The result from running the SQL query
     * @throws SQLException
     */
    public ResultSet runH2Query(String query) throws SQLException {
        String jdbcURL = "jdbc:h2:mem:bookstoredb";
        String username = "sa";
        String password = "";

        this.connection = DriverManager.getConnection(jdbcURL, username, password);

        Statement statement = connection.createStatement();

        return statement.executeQuery(query);
    }

    /**
     * Helper function that maps a ResultSet (from an SQL query)
     * into a book object.
     *
     * @param resultSet The result produce from running an SQL query
     * @return Book A book object containing the data from the query
     * @throws SQLException
     */
    public Book mapToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();

        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setPrice(resultSet.getDouble("price"));

        return book;
    }

    /**
     * Retrieves a book with a specific ID
     *
     * @param id The ID of the book
     * @return Book The book from the database with the specified ID
     */
    public Book findById(int id) throws SQLException {

        ResultSet resultSet = runH2Query("SELECT * FROM books WHERE id=" + id);

        Book book = null;
        try {
            while (resultSet.next()) {
                book = mapToBook(resultSet);
            }
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            this.connection.close();
        }

        return book;
    }


    /**
     * Retrieves all books from the database
     *
     * @return List A list containing all the books
     */
    public List<Book> findAll() throws SQLException {

        ResultSet resultSet = runH2Query("SELECT * FROM books");

        Book book = new Book();
        List<Book> bookList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                book = mapToBook(resultSet);
                bookList.add(book);
            }
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            this.connection.close();
        }

        return bookList;
    }

    public Book addBook(Book book) throws SQLException {
        Book newBook = new Book();

        String query = "INSERT INTO books VALUES ('" + book.getTitle() + "', '"
                + book.getAuthor() + "', '" + book.getIsbn() + "', " + book.getPrice() + ")";

        ResultSet resultSet = runH2Query(query);

        newBook = mapToBook(resultSet);

        return newBook;
    }

}
