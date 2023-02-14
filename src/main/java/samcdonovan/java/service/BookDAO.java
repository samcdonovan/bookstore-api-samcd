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
     * Creates a new H2 connection using H2 credentials
     * and sets class variable 'connection' to this new connection
     *
     * @throws SQLException
     */
    public void newConnection() throws SQLException {
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
    public int execute(String query) throws SQLException {
        newConnection();

        Statement statement = this.connection.createStatement();
        return statement.executeUpdate(query);
       /* while(statement.getGeneratedKeys().next()){
            System.out.println(statement.getGeneratedKeys().getInt("id"));
        }*/

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
     * Inserts a book into the H2 database
     *
     * @param book The book to be inserted
     * @return The newly inserted book
     * @throws SQLException
     */
    public Book insertBook(Book book) throws SQLException {

        String query = "INSERT INTO books (title, author, isbn, price) VALUES " +
                "('" + book.getTitle() + "', '" + book.getAuthor() + "', '"
                + book.getIsbn() + "', " + book.getPrice() + ")";

        try {
            execute(query);
            //book.setId(executedStatement.getGeneratedKeys().getInt(0));

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
     * Retrieves all books that contain the specified author name
     *
     * @param author The author of the book
     * @return List A list of all the books with author fields containing the specified author
     */
    public List<Book> findByAuthor(String author) throws SQLException {

        ResultSet resultSet = executeQuery("SELECT * FROM books " +
                "WHERE LOWER(author) LIKE LOWER('%" + author + "%')");

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
     * Retrieves all books that contain the specified title
     *
     * @param title The title of the book
     * @return List A list of all the books with title fields containing the specified title
     */
    public List<Book> findByTitle(String title) throws SQLException {

        ResultSet resultSet = executeQuery("SELECT * FROM books " +
                "WHERE LOWER(title) LIKE LOWER('%" + title + "%')");

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
     * Updates the book with the specified ID in the database
     *
     * @param book The new information for the book to be updated
     * @param id   The ID of the book to be updated
     * @return The newly updated book
     * @throws SQLException
     */
    public Book updateBook(Book book, int id) throws SQLException {

        execute("UPDATE books SET title='" + book.getTitle() +
                "', author='" + book.getAuthor() + "', isbn='" + book.getIsbn() +
                "', price=" + book.getPrice() + " WHERE id=" + id);


        this.connection.close();

        return book;
    }

    /**
     * Updates given fields of the book with the given ID
     *
     * @param book The fields to update to
     * @param id   The ID of the book to update
     * @return The newly updated book
     * @throws SQLException
     */
    public Book updateFields(Book book, int id) throws SQLException {
        String sqlQuery = "UPDATE books SET ";
        boolean first = true;

        System.out.println(book);
        if (book.getTitle() != null && !book.getTitle().isEmpty()) {
            sqlQuery += "title='" + book.getTitle() + "'";
            first = false;
        }
        if (book.getAuthor() != null && !book.getAuthor().isEmpty()) {
            if (!first) sqlQuery += ", ";

            sqlQuery += "author='" + book.getAuthor() + "'";

            if (first) first = false;
        }
        if (book.getIsbn() != null && !book.getIsbn().isEmpty()) {

            if (!first) sqlQuery += ", ";

            sqlQuery += "isbn='" + book.getIsbn() + "'";

            if (first) first = false;
        }
        if (book.getPrice() > 0.0) {

            if (!first) sqlQuery += ", ";

            sqlQuery += "price=" + book.getPrice();
        }

        sqlQuery += " WHERE id=" + id;
        execute(sqlQuery);


        this.connection.close();

        return book;
    }

    /**
     * Deletes the book with the specified ID from the database.
     *
     * @param id The ID of the book to be deleted.
     * @throws SQLException
     */
    public boolean deleteBookById(int id) throws SQLException {
        int success = execute("DELETE FROM books WHERE id=" + id);

        this.connection.close();

        return success > 0 ? true : false;
    }

    /**
     * Deletes all rows from the 'books' table
     *
     * @throws SQLException
     */
    public void deleteAllBooks() throws SQLException {
        execute("DELETE FROM books");

        this.connection.close();
    }
}
