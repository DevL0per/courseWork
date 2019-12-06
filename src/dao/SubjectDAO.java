package dao;

import model.university.Subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO extends AbstractDAO {

    private final String GET_SUBJECT_BY_ID = "SELECT * FROM Предмет WHERE КодПредмета = ?";
    private final String CREATE_SUBJECT = "INSERT INTO Предмет (Название) VALUES (?)";

    @Override
    public <T> List getAllWhere(String sql, T value) {
        List<Subject> list = new ArrayList<>();
        PreparedStatement statement = getPrepareStatement("SELECT * FROM Предмет" + " " + sql);
        try {
            if (!sql.isEmpty()) {
                statement.setObject(1, value);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("КодПредмета");
                String name = resultSet.getString("Название");
                Subject subject = new Subject(id, name);
                list.add(subject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public <T> boolean update(int id, T value, String field) {
        return false;
    }

    @Override
    public Object getEntityById(Integer id) {
        Subject subject = null;
        PreparedStatement statement = getPrepareStatement(GET_SUBJECT_BY_ID);
        try {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("Название");
                subject = new Subject(id, name);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return subject;
    }

    public Integer getSubjectIdByName(String name) {
        Integer id = null;
        PreparedStatement statement = getPrepareStatement("SELECT * FROM Предмет" +
                " WHERE Название = ?" );
        try {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("КодПредмета");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return id;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean create(Object entity) {
        PreparedStatement preparedStatement = getPrepareStatement(CREATE_SUBJECT);
        Subject subject = (Subject)entity;
        try {
            preparedStatement.setString(1, subject.getName());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
