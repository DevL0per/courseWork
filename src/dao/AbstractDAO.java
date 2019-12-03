package dao;

import ConnectionPull.ConnectionPull;

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
        ConnectionPull.shared.returnConnection(connection);
    }

    public PreparedStatement getPrepareStatement(String sql) {
        connection = ConnectionPull.shared.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        ConnectionPull.shared.returnConnection(connection);
        return preparedStatement;
    }

    public PreparedStatement getPrepareStatementWithLastSqlId(String sql) {
        connection = ConnectionPull.shared.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        ConnectionPull.shared.returnConnection(connection);
        return preparedStatement;
    }

    //public PreparedStatement getPrepareStatementWithGeneratedKe

    public void closePrepareStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException exp) {
                exp.printStackTrace();
            }
        }
    }
}
