package dao;

public class AccoutantDAO extends AbstractController {
    @Override
    public <T> boolean update(T value, String tableName, String field) {
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
