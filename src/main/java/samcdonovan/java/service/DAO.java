package samcdonovan.java.service;

import java.sql.*;
import java.util.List;

/**
 * DAO interface for common DAO functions
 *
 * @param <T> Type of DAO (in this project, only Books will be used)
 */
public interface DAO <T> {

    void setConnection(Connection connection);

    Connection getConnection();

    T get(int id) throws SQLException;

    T get(T t) throws SQLException;

    List<T> getAll() throws SQLException;

    void insert(T t) throws SQLException;

    void updateDocument(T t ) throws SQLException;

    void updateFields(T t) throws SQLException;

    void delete(T t) throws SQLException;

    void deleteAll() throws SQLException;

}