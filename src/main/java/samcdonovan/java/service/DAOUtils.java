package samcdonovan.java.service;

import samcdonovan.java.model.Book;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Utitlty class for DAOs; contains helper functions related to database operations.
 */
public class DAOUtils {

    /* H2 database credentials */
    private final static String jdbcURL = "jdbc:h2:mem:bookstoredb";
    private final static String username = "sa";
    private final static String password = "";

    /**
     * Creates a new H2 connection using H2 credentials
     * and sets class variable 'connection' to this new connection
     *
     * @throws SQLException
     */
    public static void newConnection(DAO dao) throws SQLException {
        /* setup H2 connection using credentials */
        dao.setConnection(DriverManager.getConnection(jdbcURL, username, password));
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
        newConnection(dao); // create new H2 connection

        /* create Statement object */
        Statement statement = dao.getConnection().createStatement();

        /* execute query using executeQuery */
        return statement.executeQuery(query);
    }

    /**
     * Helper function set up a H2 connection and run CREATE, UPDATE
     * and DELETE functions.
     *
     * @param query SQL query to run through H2
     * @throws SQLException
     */
    public static int executeUpdate(DAO dao, String query) throws SQLException {
        newConnection(dao); // create new H2 connection

        /* create Statement object */
        Statement statement = dao.getConnection().createStatement();

        /* execute query using executeUpdate */
        return statement.executeUpdate(query);
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
     * Adds fields to a WHERE clause in a SQL query based on the parameters (String)
     *
     * @param fieldName The name of the field to be added
     * @param fieldVal The value of the field to be added (String)
     * @param isFirst Flag to see if this is the first field in the query
     * @return String containing the field to be added to an SQL query
     */
    public static String addField(String fieldName, String fieldVal, boolean[] isFirst){
        String fieldString = "";

        /* if field is not empty, add it to the query */
        if (fieldVal != null && !fieldVal.isEmpty()) {
            /* if it is not the first field in the query, append a comma */
            if (!isFirst[0]) fieldString += ", ";

            fieldString += fieldName + "='" + fieldVal + "'";

            if(isFirst[0]) isFirst[0] = false;
        }
        return fieldString;
    }

    /**
     * Adds fields to a WHERE clause in a SQL query based on the parameters (double)
     *
     * @param fieldName The name of the field to be added
     * @param fieldVal The value of the field to be added (double)
     * @param isFirst Flag to see if this is the first field in the query
     * @return String containing the field to be added to an SQL query
     */
    public static String addField(String fieldName, double fieldVal, boolean[] isFirst){
        String fieldString = "";

        /* if double value is valid/not empty */
        if (fieldVal > 0.0) {

            if (!isFirst[0]) fieldString += ", ";

            fieldString += fieldName + "=" + fieldVal;

            if(isFirst[0]) isFirst[0] = false;
        }

        return fieldString;
    }

    /**
     * Builds an UDPATE query based on the fields passed through the book object
     *
     * @param book The fields to update the database with
     * @param id The ID of the book to be updated
     * @return String containing the SQL query
     */
    public static String buildUpdateQuery(Book book, int id){
        String sqlQuery = "UPDATE books SET ";
        boolean[] isFirst = {true};

        /* call local addField function to add the specified field to
        the query string only if it is not empty or null */
        sqlQuery += addField("title", book.getTitle(), isFirst);
        sqlQuery += addField("author", book.getAuthor(), isFirst);
        sqlQuery += addField("isbn", book.getIsbn(), isFirst);
        sqlQuery += addField("price", book.getPrice(), isFirst);

        sqlQuery += " WHERE id=" + id;

        return sqlQuery;
    }

    /**
     * Builds a SELECT query based on the fields passed through the book object
     *
     * @param params String array containing all the parameters
     * @return String containing the SQL query
     */
    public static String buildSelectQuery(String... params){
        String sqlQuery = "SELECT * FROM books WHERE ";

        String[] paramArr;
        int paramCount = 0;

        /* build list containing only the parameters which are not null */
        ArrayList<String> paramList = new ArrayList<String>();
        for (String param : params) {
            if (param!= null) paramList.add(param);
        }

        /* loop through non-null parameter list */
        for (String param : paramList) {

            /* build query by using param name and value */
            paramArr = param.split(":");
            sqlQuery += "LOWER(" + paramArr[0] + ") LIKE LOWER('%"
                    + paramArr[1] + "%')";

            /* if there are more parameters in the list, append 'AND' to query */
            if (paramCount >= 0 && paramCount < paramList.size() - 1)
                sqlQuery += " AND ";

            paramCount++;
        }

        return sqlQuery;
    }
}
