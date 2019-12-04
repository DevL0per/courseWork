package dao;

import model.AbstractUser;
import model.Role;
import model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao extends AbstractDAO {

    private final String GET_ALL_COMMAND = "SELECT * FROM Студент";
    private final String DELETE_ACCOUNT_BY_ID = "DELETE FROM УчетнаяЗапись WHERE КодУчетнойЗаписи = ?";
    private final String DELETE_STUDENT_BY_ACCOUNT_ID = "DELETE FROM Студент WHERE УчетнаяЗапись_КодУчетнойЗаписи = ?";

    private final String CREATE_ACCOUNT = "INSERT INTO УчетнаяЗапись " +
            "(Имя, Фамилия, Отчество, Телефон, Почта, Пароль, Роль) values (?, ?, ?, ?, ?, ?, ?)";
    private final String CREATE_STUDENT = "INSERT INTO Студент " +
            "(НомерСтудБилета, Группа_НомерГруппы, УчетнаяЗапись_КодУчетнойЗаписи, ФормаОбучения)" +
            " values (?, ?, ?, ?)";
    private final String UPDATE_STUDENT = "UPDATE Студент SET ";

    @Override
    public <T> List getAllWhere(String sql, T value) {
        List<Student> list = new ArrayList<>();
        PreparedStatement statement = getPrepareStatement(GET_ALL_COMMAND + " " + sql);
        PreparedStatement statement2 = getPrepareStatement("SELECT * FROM УчетнаяЗапись " +
                "WHERE КодУчетнойЗаписи = ?");
        try {
            if (sql != null) {
                statement.setObject(1, value);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Integer studentNumber = resultSet.getInt("НомерСтудБилета");
                Integer accountCode = resultSet.getInt("УчетнаяЗапись_КодУчетнойЗаписи");
                Integer numberOfGroup = resultSet.getInt("Группа_НомерГруппы");
                Double scholarship = resultSet.getDouble("Стипендия");
                String formOfTraining = resultSet.getString("ФормаОбучения");

                statement2.setInt(1, accountCode);
                ResultSet accountResult = statement2.executeQuery();

                while (accountResult.next()) {
                    String name = accountResult.getString("Имя");
                    String surname = accountResult.getString("Фамилия");
                    String patronymic = accountResult.getString("Отчество");
                    String number = accountResult.getString("Телефон");
                    String email = accountResult.getString("Почта");
                    String password = accountResult.getString("Пароль");
                    String stringRole = accountResult.getString("Роль");
                    Role role;
                    if (stringRole.equals("студент")) {
                        role = Role.STUDENT;
                    } else {
                        role = Role.BANNED;
                    }
                    Student student = new Student(name, surname, patronymic, number,
                            email, password, accountCode, numberOfGroup, studentNumber,
                            scholarship, formOfTraining, role);
                    list.add(student);
                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return list;
    }

    @Override
    public <T> boolean update(int id, T value, String field) {
        PreparedStatement preparedStatement = getPrepareStatement(UPDATE_STUDENT
                + field + " = ? WHERE НомерСтудБилета = ?");
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
        Student student = null;
        PreparedStatement statement = getPrepareStatement("SELECT * FROM Студент" +
                " WHERE НомерСтудБилета = ?" );
        PreparedStatement statement2 = getPrepareStatement("SELECT * FROM УчетнаяЗапись " +
                "WHERE КодУчетнойЗаписи = ?");
        try {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer studentNumber = resultSet.getInt("НомерСтудБилета");
                Integer accountCode = resultSet.getInt("УчетнаяЗапись_КодУчетнойЗаписи");
                Integer numberOfGroup = resultSet.getInt("Группа_НомерГруппы");
                Double scholarship = resultSet.getDouble("Стипендия");
                String formOfTraining = resultSet.getString("ФормаОбучения");

                statement2.setInt(1, accountCode);
                ResultSet accountResult = statement2.executeQuery();

                while (accountResult.next()) {
                    String name = accountResult.getString("Имя");
                    String surname = accountResult.getString("Фамилия");
                    String patronymic = accountResult.getString("Отчество");
                    String number = accountResult.getString("Телефон");
                    String email = accountResult.getString("Почта");
                    String password = accountResult.getString("Пароль");
                    String stringRole = accountResult.getString("Роль");
                    Role role;
                    if (stringRole.equals("студент")) {
                        role = Role.STUDENT;
                    } else {
                        role = Role.BANNED;
                    }
                    student = new Student(name, surname, patronymic, number,
                            email, password, accountCode, numberOfGroup, studentNumber,
                            scholarship, formOfTraining, role);
                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return student;
    }

    @Override
    public boolean delete(Integer id) {
        PreparedStatement deleteAccountStatement = getPrepareStatement(DELETE_ACCOUNT_BY_ID);
        PreparedStatement deleteStudentStatement = getPrepareStatement(DELETE_STUDENT_BY_ACCOUNT_ID);
        try {
            deleteStudentStatement.setInt(1, id);
            deleteStudentStatement.executeUpdate();

            // Delete account
            deleteAccountStatement.setInt(1, id);
            deleteAccountStatement.executeUpdate();
        } catch (SQLException exp) {
            exp.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean create(Object entity) {
        PreparedStatement userStatement = getPrepareStatementWithLastSqlId(CREATE_ACCOUNT);
        if (entity instanceof Student) {
            Student student = (Student) entity;
            PreparedStatement statement = getPrepareStatement("INSERT INTO Студент " +
                    "(НомерСтудБилета, Группа_НомерГруппы, УчетнаяЗапись_КодУчетнойЗаписи, ФормаОбучения)" +
                    " values (?, ?, ?, ?)");
            try {

                initializeUserStatement(userStatement, student);
                userStatement.setString(7, "студент");
                userStatement.executeUpdate();

                ResultSet idResultSet = userStatement.getGeneratedKeys();
                while (idResultSet.next()) {
                    int accountCode = idResultSet.getInt(1);
                    student.setAccountCode(accountCode);
                    break;
                }

                statement.setInt(1, student.getStudentNumber());
                statement.setInt(2, student.getNumberOfGroup());
                statement.setInt(3, student.getAccountCode());
                statement.setString(4, student.getFormOfTraining());

                statement.executeUpdate();
            } catch (SQLException exp) {
                exp.printStackTrace();
            }
        }
        return true;
    }

    public void initializeUserStatement(PreparedStatement statement, AbstractUser user) {
        try {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPatronymic());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getPassword());
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
    }

    public void addStudentScholarshipById(Integer userId, Double scholarship) {
        PreparedStatement statement = getPrepareStatement(UPDATE_STUDENT + " Стипендия = ? WHERE НомерСтудБилета = ?");
        try {
            statement.setDouble(1, scholarship);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
