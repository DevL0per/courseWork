package dao;

import ConnectionPull.ConnectionPull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractController {
    private Connection connection;

    public AbstractController() {
    }

    public abstract <T> boolean update(T value, String tableName, String field);
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