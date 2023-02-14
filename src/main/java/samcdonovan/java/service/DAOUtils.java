package samcdonovan.java.service;

import samcdonovan.java.model.Book;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

/**
 * Utitlty class for DAOs; contains helper functions related to database operations.
 */
public class DAOUtils {

    /**
     * Creates a new H2 connection using H2 credentials
     * and sets class variable 'connection' to this new connection
     *
     * @throws SQLException
     */
    public static void newConnection(DAO dao) throws SQLException {
        String jdbcURL = "jdbc:h2:mem:bookstoredb";
        String username = "sa";
        String password = "";

        dao.setConnection(DriverManager.getConnection(jdbcURL, username, password));
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
    public static ResultSet executeQuery(DAO dao, String query) throws SQLException {
        newConnection(dao);

        Statement statement = dao.getConnection().createStatement();

        return statement.executeQuery(query);
    }

    /**
     * Helper function set up a H2 connection and run CREATE, UPDATE
     * and DELETE functions.
     *
     * @param query SQL query to run through H2
     * @throws SQLException
     */
    public static int execute(DAO dao, String query) throws SQLException {
        newConnection(dao);

        Statement statement = dao.getConnection().createStatement();
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
    public static Book mapToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();

        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setPrice(resultSet.getDouble("price"));

        return book;
    }

    /**
     * Adds fields to an SQL query based on the parameters
     *
     * @param fieldName The name of the field to be added
     * @param fieldVal The value of the field to be added (String)
     * @param isFirst Flag to see if this is the first field in the query or not
     * @return String containing the field to be added to an SQL query
     */
    public static String addField(String fieldName, String fieldVal, boolean[] isFirst){
        String fieldString = "";
        if (fieldVal != null && !fieldVal.isEmpty()) {
            if (!isFirst[0]) fieldString += ", ";

            fieldString += fieldName + "='" + fieldVal + "'";

            if(isFirst[0]) isFirst[0] = false;
        }
        return fieldString;
    }

    /**
     * Adds fields to an SQL query based on the parameters
     *
     * @param fieldName The name of the field to be added
     * @param fieldVal The value of the field to be added (double)
     * @param isFirst Flag to see if this is the first field in the query or not
     * @return String containing the field to be added to an SQL query
     */
    public static String addField(String fieldName, double fieldVal, boolean[] isFirst){
        String fieldString = "";

        if (fieldVal > 0.0) {

            if (!isFirst[0]) fieldString += ", ";

            fieldString += fieldName + "=" + fieldVal;

            if(isFirst[0]) isFirst[0] = false;
        }

        return fieldString;
    }

    /**
     * Builds an update query based on the fields passed through the book object
     *
     * @param book The fields to update the database with
     * @param id The ID of the book to be updated
     * @return String containing the SQL query
     */
    public static String buildQuery(Book book, int id){
        String sqlQuery = "UPDATE books SET ";
        boolean[] isFirst = {true};

        sqlQuery += addField("title", book.getTitle(), isFirst);
        sqlQuery += addField("author", book.getAuthor(), isFirst);
        sqlQuery += addField("isbn", book.getIsbn(), isFirst);
        sqlQuery += addField("price", book.getPrice(), isFirst);

        sqlQuery += " WHERE id=" + id;

        return sqlQuery;
    }
}
