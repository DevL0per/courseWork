package dao;

import model.university.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO extends AbstractDAO {

    private final String GET_ALL_GROUPS = "SELECT * FROM Группа";

    @Override
    public List getAll() {
        List<Group> list = new ArrayList<>();
        PreparedStatement statement = getPrepareStatement(GET_ALL_GROUPS);
        try {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("НомерГруппы");
                Integer course = resultSet.getInt("Курс");
                Integer specialtyId = resultSet.getInt("Специальность_КодСпециальности");
                Group group = new Group(id, course, specialtyId);
                list.add(group);
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
        return false;
    }
}
