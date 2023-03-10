package samcdonovan.java.service;

import java.sql.*;
import java.util.List;

/**
 * DAO interface for common DAO functions
 *
 * @param <T> Type of DAO (in this project, only Books will be used)
 */
public interface DAO<T> {

    void setConnection(Connection connection);

    Connection getConnection();

    List<T> getAll() throws SQLException;

    T get(int id) throws SQLException;

    List<T> get(String... t) throws SQLException;

    void insert(T t) throws SQLException;

    T updateDocument(T t, int id) throws SQLException;

    T updateFields(T t, int id) throws SQLException;

    boolean delete(int id) throws SQLException;

    void deleteAll() throws SQLException;

}
