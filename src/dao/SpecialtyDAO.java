package dao;

import model.university.Specialty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecialtyDAO extends AbstractDAO {

    private final String GET_SPECIALTY_BY_ID = "SELECT * FROM Специальность WHERE КодСпециальности = ?";
    private final String ADD_SPECIALTY = "INSERT INTO Специальность (Название, Факультет_КодФакультета) " +
            "VALUES (?, ?)";

    @Override
    public <T> List getAllWhere(String sql, T value) {
        return null;
    }

    @Override
    public <T> boolean update(int id, T value, String field) {
        return false;
    }

    @Override
    public Object getEntityById(Integer id) {
        Specialty specialty = null;
        PreparedStatement statement = getPrepareStatement(GET_SPECIALTY_BY_ID);
        try {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("Название");
                Integer facultyCode = resultSet.getInt("Факультет_КодФакультета");
                specialty = new Specialty(id, name, facultyCode);
            }
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        return specialty;
    }

    public List getAllSpecialtyInFaculty(Integer facultyId) {
        List<Specialty> list = new ArrayList<>();
        PreparedStatement statement = getPrepareStatement("SELECT * FROM Специальность WHERE" +
                " Факультет_КодФакультета = ?");
        try {
            statement.setInt(1, facultyId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("КодСпециальности");
                String name = resultSet.getString("Название");
                Specialty specialty = new Specialty(id, name, facultyId);
                list.add(specialty);
            }
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean create(Object entity) {
        PreparedStatement preparedStatement = getPrepareStatement(ADD_SPECIALTY);
        Specialty specialty = (Specialty)entity;
        try {
            preparedStatement.setString(1, specialty.getName());
            preparedStatement.setInt(2, specialty.getFacultyId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
