package dao;

import model.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class AccountDAO extends AbstractDAO {

    private final String UPDATE_ACCOUNT = "UPDATE УчетнаяЗапись SET ";

    public Integer getIdByAccountIdByLoginAndPassword(String login, String password) {
        PreparedStatement statement = getPrepareStatement("SELECT * FROM УчетнаяЗапись WHERE Почта= ? AND Пароль=?");
        try {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("КодУчетнойЗаписи");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return 0;
    }

    public Boolean isUserExist(String login, String password) {
        PreparedStatement statement = getPrepareStatement("SELECT * FROM УчетнаяЗапись WHERE Почта=? AND Пароль=?");
        try {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else  {
                return false;
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            return false;
        } finally {
            closePrepareStatement(statement);
        }
    }

    public Role getRoleByLoginAndPassword(String login, String password) {
        Role role = null;
        PreparedStatement statement = getPrepareStatement("SELECT * FROM УчетнаяЗапись WHERE Почта=? AND Пароль=?");
        try {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("Роль").equals("студент")) {
                    role = Role.STUDENT;
                } else {
                    role = Role.ACCOUNTANT;
                }
                break;
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return role;
    }

    @Override
    public <T> List getAllWhere(String sql, T value) {
        return null;
    }

    @Override
    public <T> boolean update(int id, T value, String field) {
        PreparedStatement preparedStatement = getPrepareStatement(UPDATE_ACCOUNT
                + field + " = ? WHERE КодУчетнойЗаписи = ?");
        try {
            preparedStatement.setObject(1, value);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Object getEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean create(Object entity) {
        return false;
    }
}
