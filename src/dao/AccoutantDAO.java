package dao;

import java.util.List;

public class AccoutantDAO extends AbstractDAO {

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
