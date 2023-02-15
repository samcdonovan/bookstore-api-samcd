package samcdonovan.java.service;

import samcdonovan.java.model.Book;
import samcdonovan.java.service.DAOUtils;

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
            DAOUtils.execute(this, query);
            //book.setId(executedStatement.getGeneratedKeys().getInt(0));

        } catch (Exception exception) {
            System.out.println(exception);
        } finally {
            this.connection.close();
        }
    }

    /**
     * Retrieves all books from the database
     *
     * @return List A list containing all the books
     */
    public List<Book> getAll() throws SQLException {

        ResultSet resultSet = DAOUtils.executeQuery(this, "SELECT * FROM books");

        Book book = new Book();
        List<Book> bookList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                book = DAOUtils.mapToBook(resultSet);
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
    public Book get(int id) throws SQLException {

        ResultSet resultSet = DAOUtils.executeQuery(this, "SELECT * FROM books WHERE id=" + id);

        Book book = null;
        try {
            while (resultSet.next()) {
                book = DAOUtils.mapToBook(resultSet);
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
     * @param params The query parameters passed into the GET path
     * @return List A list of all the books with author fields containing the specified author
     */
    public List<Book> get(String... params) throws SQLException {

        String sqlQuery = "SELECT * FROM books WHERE ";

        String[] paramArr;
        int paramCount = 0;

        ArrayList<String> paramList = new ArrayList<String>();
        for (String param : params) {
            if (param!= null) paramList.add(param);
        }

        for (String param : paramList) {

            paramArr = param.split(":");
            sqlQuery += "LOWER(" + paramArr[0] + ") LIKE LOWER('%"
                    + paramArr[1] + "%')";

            if (paramCount >= 0 && paramCount < paramList.size() - 1) sqlQuery += " AND ";
            paramCount++;
        }

        System.out.println(sqlQuery);
        ResultSet resultSet = DAOUtils.executeQuery(this, sqlQuery);
        Book book = new Book();
        List<Book> bookList = new ArrayList<>();

        try {
            while (resultSet.next()) {

                book = DAOUtils.mapToBook(resultSet);
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
    public Book updateDocument(Book book, int id) throws SQLException {

        DAOUtils.execute(this, "UPDATE books SET title='" + book.getTitle() +
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
        String sqlQuery = DAOUtils.buildQuery(book, id);

        DAOUtils.execute(this, sqlQuery);

        this.connection.close();

        return book;
    }

    /**
     * Deletes the book with the specified ID from the database.
     *
     * @param id The ID of the book to be deleted.
     * @throws SQLException
     */
    public boolean delete(int id) throws SQLException {
        int success = DAOUtils.execute(this, "DELETE FROM books WHERE id=" + id);

        this.connection.close();

        return success > 0 ? true : false;
    }

    /**
     * Deletes all rows from the 'books' table
     *
     * @throws SQLException
     */
    public void deleteAll() throws SQLException {
        DAOUtils.execute(this, "DELETE FROM books");

        this.connection.close();
    }
}
