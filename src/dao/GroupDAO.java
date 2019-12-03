package dao;

import model.university.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO extends AbstractDAO {

    private final String GET_ALL_GROUPS = "SELECT * FROM Группа";
    private final String CREATE_GROUP = "INSERT INTO Группа " +
            "(НомерГруппы, Курс, Специальность_КодСпециальности) VALUES (?, ?, ?)";
    private final String DELETE_GROUP = "DELETE FROM Группа WHERE НомерГруппы = ?";


    @Override
    public <T> List getAllWhere(String sql, T value) {
        //Специальность_КодСпециальности = ?;
        List<Group> list = new ArrayList<>();
        PreparedStatement statement = getPrepareStatement("SELECT * FROM Группа " + sql);
        try {
            if (!sql.isEmpty()) {
                statement.setObject(1, value);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer groupNumber = resultSet.getInt("НомерГруппы");
                Integer course = resultSet.getInt("Курс");
                Integer specialty = resultSet.getInt("Специальность_КодСпециальности");
                Group group = new Group(groupNumber, course, specialty);
                list.add(group);
            }
            closePrepareStatement(statement);
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        return list;
    }


    @Override
    public <T> boolean update(int id, T value, String field) {
        return false;
    }

    @Override
    public Object getEntityById(Integer id) {
        Group group = null;
        PreparedStatement statement = getPrepareStatement("SELECT * FROM Группа WHERE" +
                " НомерГруппы = ?");
        try {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer groupNumber = resultSet.getInt("НомерГруппы");
                Integer course = resultSet.getInt("Курс");
                Integer specialtyId = resultSet.getInt("Специальность_КодСпециальности");
                group = new Group(groupNumber, course, specialtyId);
            }
            closePrepareStatement(statement);
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        return group;
    }

    @Override
    public boolean delete(Integer id) {
        PreparedStatement preparedStatement = getPrepareStatement(DELETE_GROUP);
        try {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            //TODO Удалить студентов в группе
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean create(Object entity) {
        PreparedStatement preparedStatement = getPrepareStatement(CREATE_GROUP);
        Group group = (Group) entity;
        try {
            preparedStatement.setInt(1, group.getNumberOfGroup());
            preparedStatement.setInt(2, group.getCourse());
            preparedStatement.setInt(3, group.getSpecialtyId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List getAllGroupInSpecialty(Integer specialtyId) {
        List<Group> list = new ArrayList<>();
        PreparedStatement statement = getPrepareStatement("SELECT * FROM Группа WHERE" +
                " Специальность_КодСпециальности = ?");
        try {
            statement.setInt(1, specialtyId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer groupNumber = resultSet.getInt("НомерГруппы");
                Integer course = resultSet.getInt("Курс");
                Group group = new Group(groupNumber, course, specialtyId);
                list.add(group);
            }
            closePrepareStatement(statement);
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        return list;
    }
}
