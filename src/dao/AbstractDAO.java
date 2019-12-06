package dao;

import ConnectionPool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDAO {
    private Connection connection;

    public AbstractDAO() {
    }

    public abstract <T> List getAllWhere(String sql, T value);
    public abstract <T> boolean update(int id, T value, String field);
    public abstract Object getEntityById(Integer id);
    public abstract boolean delete(Integer id);
    public abstract boolean create(Object entity);

    public void returnConnectionInPool() {
        ConnectionPool.shared.returnConnection(connection);
    }

    public PreparedStatement getPrepareStatement(String sql) {
        connection = ConnectionPool.shared.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        ConnectionPool.shared.returnConnection(connection);
        return preparedStatement;
    }

    public PreparedStatement getPrepareStatementWithLastSqlId(String sql) {
        connection = ConnectionPool.shared.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        ConnectionPool.shared.returnConnection(connection);
        return preparedStatement;
    }
}
