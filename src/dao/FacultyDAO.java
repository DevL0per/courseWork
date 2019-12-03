package dao;

import model.university.Faculty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacultyDAO extends AbstractDAO {

    private final String GET_ALL_FACULTY = "SELECT * FROM Факультет";
    private final String ADD_FACULTY = "INSERT INTO Факультет (Название) VALUES (?)";

    @Override
    public <T> List getAllWhere(String sql, T value) {
        List<Faculty> list = new ArrayList<>();
        PreparedStatement statement = getPrepareStatement(GET_ALL_FACULTY + sql);
        try {
            if (!sql.isEmpty()) {
                statement.setObject(1, value);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("КодФакультета");
                String name = resultSet.getString("Название");
                Faculty faculty = new Faculty(id, name);
                list.add(faculty);
            }

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
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean create(Object entity) {
        PreparedStatement preparedStatement = getPrepareStatement(ADD_FACULTY);
        Faculty faculty = (Faculty) entity;
        try {
            preparedStatement.setString(1, faculty.getName());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
