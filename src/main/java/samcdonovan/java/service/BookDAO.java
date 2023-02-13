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

    public void newConnection() throws SQLException{
        String jdbcURL = "jdbc:h2:mem:bookstoredb";
        String username = "sa";
        String password = "";

        this.connection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("H2 connection has started!");

    }

    /**
     * Helper function to set up a H2 connection and then
     * run the 'executeQuery' function to return elements from the database.
     *
     * @param query SQL query to run through H2
     * @return ResultSet The result from running the SQL query
     * @throws SQLException
     */
    public ResultSet executeQuery(String query) throws SQLException {
        newConnection();

        Statement statement = this.connection.createStatement();

        return statement.executeQuery(query);
    }

    /**
     * Helper function set up a H2 connection and run CREATE, UPDATE
     * and DELETE functions.
     *
     * @param query SQL query to run through H2
     * @throws SQLException
     */
    public void execute(String query) throws SQLException {
        newConnection();

        Statement statement = this.connection.createStatement();
        statement.execute(query);
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

        ResultSet resultSet = executeQuery("SELECT * FROM books WHERE id=" + id);

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

        ResultSet resultSet = executeQuery("SELECT * FROM books");

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

    /**
     * Inserts a book into the H2 database
     *
     * @param book The book to be inserted
     * @return The newly inserted book
     * @throws SQLException
     */
    public Book addBook(Book book) throws SQLException {

        String query = "INSERT INTO books (title, author, isbn, price) VALUES " +
                "('" + book.getTitle() + "', '" + book.getAuthor() + "', '"
                + book.getIsbn() + "', " + book.getPrice() + ")";

        try {
            //ResultSet resultSet = runH2Query(query);

            execute(query);
            //newBook = mapToBook(resultSet);
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            this.connection.close();
        }

        return book;
    }

    /**
     * Updates the book with the specified ID in the database
     *
     * @param book The new information for the book to be updated
     * @param id   The ID of the book to be updated
     * @return The newly updated book
     * @throws SQLException
     */
    public Book updateBook(Book book, int id) throws SQLException {

        Book updatedBook = new Book();
        execute("UPDATE books SET title='" + book.getTitle() +
                "', author='" + book.getAuthor() + "', isbn='" + book.getIsbn() +
                "', price=" + book.getPrice() + " WHERE id=" + id);


        this.connection.close();

        return updatedBook;
    }

    /**
     * Deletes the book with the specified ID from the database.
     *
     * @param id The ID of the book to be deleted.
     * @throws SQLException
     */
    public void deleteBookById(int id) throws SQLException {
        execute("DELETE FROM books WHERE id=" + id);

        this.connection.close();
    }

}
