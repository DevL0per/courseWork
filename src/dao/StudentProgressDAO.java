package dao;

import model.university.StudentProgress;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentProgressDAO extends AbstractDAO {

    private final String GET_STUDENT_PROGRESS_BY_ID = "SELECT * FROM Успеваемость WHERE КодОценки = ?";
    private final String UPDATE_STUDENT_PROGRESS = "UPDATE Успеваемость SET ";
    private final String GET_ALL_STUDENT_PROGRESS = "SELECT * FROM Успеваемость";

    @Override
    public <T> List getAllWhere(String sql, T value) {
        List<StudentProgress> list = new ArrayList<>();
        PreparedStatement statement = getPrepareStatement(GET_ALL_STUDENT_PROGRESS + " " + sql);
        try {
            if (!sql.isEmpty()) {
                statement.setObject(1, value);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("КодОценки");
                int grade = resultSet.getInt("Оценка");
                int semester = resultSet.getInt("НомерСеместра");
                int studentNumber = resultSet.getInt("Студент_НомерСтудБилета");
                int subjectId = resultSet.getInt("Предмет_КодПредмета");
                StudentProgress studentProgress = new StudentProgress(id, grade, subjectId,
                        studentNumber, semester);
                list.add(studentProgress);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public <T> boolean update(int id, T value, String field) {
        PreparedStatement preparedStatement = getPrepareStatement(UPDATE_STUDENT_PROGRESS
                + field + " = ? WHERE КодОценки = ?");
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
        PreparedStatement preparedStatement = getPrepareStatement(GET_STUDENT_PROGRESS_BY_ID);
        StudentProgress studentProgress = null;
        try {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int grade = resultSet.getInt("Оценка");
                int semester = resultSet.getInt("НомерСеместра");
                int studentNumber = resultSet.getInt("Студент_НомерСтудБилета");
                int subjectId = resultSet.getInt("Предмет_КодПредмета");
                studentProgress = new StudentProgress(id, grade, semester,
                        studentNumber, subjectId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentProgress;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean create(Object entity) {
        PreparedStatement statement = getPrepareStatement("INSERT INTO Успеваемость " +
                "(Оценка, НомерСеместра, Студент_НомерСтудБилета, Предмет_КодПредмета) values (?, ?, ?, ?) ");
        StudentProgress studentProgress = (StudentProgress) entity;
        try {
            statement.setInt(1, studentProgress.getGrade());
            statement.setInt(2, studentProgress.getNumberOfSemester());
            statement.setInt(3, studentProgress.getNumberOfStudent());
            statement.setInt(4, studentProgress.getNumberOfSubject());

            statement.executeUpdate();
            closePrepareStatement(statement);
        } catch (Exception exp) {
            exp.printStackTrace();
            return false;
        }
        return true;
    }
}
