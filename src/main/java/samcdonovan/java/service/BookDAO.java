package samcdonovan.java.service;

import samcdonovan.java.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Books
 */
public class BookDAO implements DAO<Book> {

    private Connection connection; // SQL connection variable

    /**
     * Empty constructor
     */
    public BookDAO() {
    }

    /* Getter and setter for H2 connection variable */
    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts a book into the H2 database
     *
     * @param book The book to be inserted
     * @throws SQLException
     */
    public void insert(Book book) throws SQLException {

        String query = "INSERT INTO books (title, author, isbn, price) VALUES " +
                "('" + book.getTitle() + "', '" + book.getAuthor() + "', '"
                + book.getIsbn() + "', " + book.getPrice() + ")";

        try {
            DAOUtils.executeUpdate(this, query);
            //book.setId(executedStatement.getGeneratedKeys().getInt(0));

        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            this.connection.close(); // close H2 connection
        }
    }

    /**
     * Retrieves all books from the database
     *
     * @return List A list containing all the books
     */
    public List<Book> getAll() throws SQLException {

        /* execute SELECT all query */
        ResultSet resultSet = DAOUtils.executeQuery(this, "SELECT * FROM books");

        Book book = new Book();
        List<Book> bookList = new ArrayList<>();

        try {

            /* map resultSet to book objects and add to list */
            while (resultSet.next()) {
                book = DAOUtils.mapToBook(resultSet);
                bookList.add(book);
            }
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            this.connection.close(); // close H2 connection
        }

        return bookList;
    }

    /**
     * Retrieves a book with a specific ID
     *
     * @param id The ID of the book
     * @return Book The book from the database with the specified ID
     */
    public Book get(int id) throws SQLException {

        /* execute H2 SELECT query */
        ResultSet resultSet = DAOUtils.executeQuery(this, "SELECT * FROM books WHERE id=" + id);

        Book book = null;
        try {
            /* map result onto book object */
            while (resultSet.next()) {
                book = DAOUtils.mapToBook(resultSet);
            }
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            this.connection.close(); // close H2 connection
        }

        return book;
    }

    /**
     * Retrieves all books that contain the specified author name
     *
     * @param params The query parameters passed into the GET path
     * @return List A list of all the books with author fields containing the specified author
     */
    public List<Book> get(String... params) throws SQLException {

        /* build the query */
        String sqlQuery = DAOUtils.buildSelectQuery(params);

        /* execute the query */
        ResultSet resultSet = DAOUtils.executeQuery(this, sqlQuery);
        Book book = new Book();
        List<Book> bookList = new ArrayList<>();

        try {
            /* map resultSet to book objects and add to list */
            while (resultSet.next()) {
                book = DAOUtils.mapToBook(resultSet);
                bookList.add(book);
            }
        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            this.connection.close(); // close H2 connection
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
    public Book updateDocument(Book book, int id) throws SQLException {

        /* execute UPDATE query; since this is a PUT request, all fields must be sent */
        DAOUtils.executeUpdate(this, "UPDATE books SET title='" + book.getTitle() +
                "', author='" + book.getAuthor() + "', isbn='" + book.getIsbn() +
                "', price=" + book.getPrice() + " WHERE id=" + id);


        this.connection.close(); // close H2 connection

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

        /* build UPDATE query and execute */
        String sqlQuery = DAOUtils.buildUpdateQuery(book, id);

        DAOUtils.executeUpdate(this, sqlQuery);

        this.connection.close(); // close H2 connection

        return book;
    }

    /**
     * Deletes the book with the specified ID from the database.
     *
     * @param id The ID of the book to be deleted.
     * @throws SQLException
     */
    public boolean delete(int id) throws SQLException {

        /* execute DELETE query */
        int success = DAOUtils.executeUpdate(this, "DELETE FROM books WHERE id=" + id);

        this.connection.close(); // close H2 connection

        return success > 0 ? true : false;
    }

    /**
     * Deletes all rows from the 'books' table
     *
     * @throws SQLException
     */
    public void deleteAll() throws SQLException {

        /* execute DELETE all query */
        DAOUtils.executeUpdate(this, "DELETE FROM books");

        this.connection.close(); // close H2 connection
    }
}
