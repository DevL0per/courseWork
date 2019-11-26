package dao;

import model.AbstractUser;
import model.Accountant;
import model.Role;
import model.Student;
import model.university.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDAO extends AbstractController {

    private final String DELETE_ACCOUNT_BY_ID = "DELETE FROM УчетнаяЗапись WHERE КодУчетнойЗаписи = ?";
    private final String DELETE_STUDENT_BY_ACCOUNT_ID = "DELETE FROM Студент WHERE УчетнаяЗапись_КодУчетнойЗаписи = ?";
    private final String DELETE_ACCOUNTANT_BY_ACCOUNT_ID = "DELETE FROM Бухгалтер WHERE УчетнаяЗапись_КодУчетнойЗаписи = ?";

    private final String GET_ALL_COMMAND = "SELECT * FROM Студент";
    private final String GET_ROLE_BY_ID = "SELECT * FROM УчетнаяЗапись WHERE КодУчетнойЗаписи = ?";
    private final String ADD_STUDENT_COMMAND = "INSERT INTO Студент";

    private final String GET_ALL_FACULTY = "SELECT * FROM Факультет";
    private final String GET_ALL_GROUPS = "SELECT * FROM Группа";

    public UserDAO() { }

    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        PreparedStatement statement = getPrepareStatement("SELECT * FROM Предмет");
        try {
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

    public void addStudentScholarshipById(Integer userId, Double scholarship) {
        PreparedStatement statement = getPrepareStatement("UPDATE Студент SET Стипендия = ?" +
                " WHERE УчетнаяЗапись_КодУчетнойЗаписи = ?");
        try {
            statement.setDouble(1, scholarship);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public <T> boolean update(T value, String tableName, String field) {
        PreparedStatement statement = getPrepareStatement("INSERT " + field +
                "INTO " + tableName + " values(?)");
        try {
            if (value instanceof Integer) {
                statement.setInt(1, (Integer)value);
            } else if (value instanceof String) {
                statement.setString(1, (String) value);
            }
        } catch (SQLException exp) {
            exp.printStackTrace();
            return false;
        }
        return true;
    }

    public <T> List getAllStudentsWhere(String addedCommand, T value) {
        List<Student> list = new ArrayList<>();
        PreparedStatement statement = getPrepareStatement(GET_ALL_COMMAND + " " + addedCommand);
        PreparedStatement statement2 = getPrepareStatement("SELECT * FROM УчетнаяЗапись " +
                "WHERE КодУчетнойЗаписи = ?");
        try {
            if (addedCommand != null) {
                statement.setObject(1, value);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Integer studentNumber = resultSet.getInt("НомерСтудБилета");
                Integer accountCode = resultSet.getInt("УчетнаяЗапись_КодУчетнойЗаписи");
                Integer numberOfGroup = resultSet.getInt("Группа_НомерГруппы");
                Double scholarship = resultSet.getDouble("Стипендия");

                statement2.setInt(1, accountCode);
                ResultSet accountResult = statement2.executeQuery();

                while (accountResult.next()) {
                    String name = accountResult.getString("Имя");
                    String surname = accountResult.getString("Фамилия");
                    String patronymic = accountResult.getString("Отчество");
                    String number = accountResult.getString("Телефон");
                    String email = accountResult.getString("Почта");
                    String password = accountResult.getString("Пароль");
                    Student student = new Student(name, surname, patronymic, number,
                            email, password, accountCode, numberOfGroup, studentNumber, scholarship);
                    list.add(student);
                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return list;
    }

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

    public List getAllFaculty() {
        List<Faculty> list = new ArrayList<>();
        PreparedStatement statement = getPrepareStatement(GET_ALL_FACULTY);
        try {
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

    public Group getGroupById(Integer numberOfGroup) {
        Group group = null;
        PreparedStatement statement = getPrepareStatement("SELECT * FROM Группа WHERE" +
                " НомерГруппы = ?");
        try {
            statement.setInt(1, numberOfGroup);
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

    public Specialty getSpecialtyById(Integer numberOfSpecialty) {
        Specialty specialty = null;
        PreparedStatement statement = getPrepareStatement("SELECT * FROM Специальность WHERE" +
                " КодСпециальности = ?");
        try {
            statement.setInt(1, numberOfSpecialty);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("Название");
                Integer facultyCode = resultSet.getInt("Факультет_КодФакультета");
                specialty = new Specialty(numberOfSpecialty, name, facultyCode);
            }
            closePrepareStatement(statement);
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
            closePrepareStatement(statement);
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        return list;
    }

    public List getAllGroups() {
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
    public Object getEntityById(Integer id) {
        return null;
    }

    public Object getStudentById(Integer id) {
        Student student = null;
        PreparedStatement statement = getPrepareStatement("SELECT * FROM Студент " +
                "WHERE УчетнаяЗапись_КодУчетнойЗаписи = ?" );
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

                statement2.setInt(1, id);
                ResultSet accountResult = statement2.executeQuery();

                while (accountResult.next()) {
                    String name = accountResult.getString("Имя");
                    String surname = accountResult.getString("Фамилия");
                    String patronymic = accountResult.getString("Отчество");
                    String number = accountResult.getString("Телефон");
                    String email = accountResult.getString("Почта");
                    String password = accountResult.getString("Пароль");
                    student = new Student(name, surname, patronymic, number,
                            email, password, accountCode, numberOfGroup, studentNumber, scholarship);
                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return student;
    }

    public Integer getSubjectIdByName(String name) {
        Integer id = null;
        PreparedStatement statement = getPrepareStatement("SELECT * FROM Предмет" +
                "WHERE Название = ?" );
        try {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("КодПредмета");
            }
            closePrepareStatement(statement);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return id;
    }


    public boolean addGrades(StudentProgress studentProgress) {
        PreparedStatement statement = getPrepareStatement("INSERT INTO Успеваемость " +
                "(Оценка, Предмет_КодПредмета, Студент_НомерСтудБилета) values (?, ?, ?) ");
        try {
            statement.setInt(1, studentProgress.getGrade());
            statement.setInt(2, studentProgress.getNumberOfSubject());
            statement.setInt(3, studentProgress.getNumberOfStudent());

            statement.executeUpdate();
            closePrepareStatement(statement);
        } catch (Exception exp) {
            exp.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        PreparedStatement statement = getPrepareStatement(DELETE_ACCOUNT_BY_ID);
        PreparedStatement getRoleStatement  = getPrepareStatement(GET_ROLE_BY_ID);
        try {
            // Delete student or accountant
            getRoleStatement.setInt(1, id);
            ResultSet resultSet = getRoleStatement.executeQuery();
            while (resultSet.next()) {
                PreparedStatement preparedStatement;
                if (resultSet.getString("Роль").equals("студент")) {
                    preparedStatement = getPrepareStatement(DELETE_STUDENT_BY_ACCOUNT_ID);
                } else {
                    preparedStatement = getPrepareStatement(DELETE_ACCOUNTANT_BY_ACCOUNT_ID);
                }
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
            // Delete account
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException exp) {
            exp.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean create(Object entity) {
        PreparedStatement statement2 = getPrepareStatement("SELECT * FROM УчетнаяЗапись WHERE Почта=?");
        PreparedStatement userStatement = getPrepareStatement("INSERT INTO УчетнаяЗапись " +
                "(Имя, Фамилия, Отчество, Телефон, Почта, Пароль, Роль) values (?, ?, ?, ?, ?, ?, ?)");
        if (entity instanceof Student) {
            Student student = (Student) entity;
            PreparedStatement statement = getPrepareStatement("INSERT INTO Студент " +
                    "(НомерСтудБилета, Группа_НомерГруппы, УчетнаяЗапись_КодУчетнойЗаписи) values (?, ?, ?)");
            try {

                initializeUserStatement(userStatement, student);
                userStatement.setString(7, "студент");
                userStatement.executeUpdate();

                statement2.setString(1, student.getEmail());
                ResultSet resultSet =  statement2.executeQuery();
                Integer accountCode = 0;

                while (resultSet.next()) {
                    accountCode = resultSet.getInt("КодУчетнойЗаписи");
                    break;
                }

                student.setAccountCode(accountCode);

                statement.setInt(1, student.getStudentNumber());
                statement.setInt(2, student.getNumberOfGroup());
                statement.setInt(3, student.getAccountCode());

                statement.executeUpdate();
            } catch (SQLException exp) {
                exp.printStackTrace();
            }
        } else {
            Accountant accountant = (Accountant) entity;
            PreparedStatement statement = getPrepareStatement("INSERT INTO Бухгалтер " +
                    "(УчетнаяЗапись_КодУчетнойЗаписи) values (?)");
            try {
                initializeUserStatement(userStatement, accountant);
                userStatement.setString(7, "бухгалтер");
                userStatement.executeUpdate();

                statement2.setString(1, accountant.getEmail());
                ResultSet resultSet =  statement2.executeQuery();
                Integer accountCode = 0;

                while (resultSet.next()) {
                    accountCode = resultSet.getInt("КодУчетнойЗаписи");
                    break;
                }

                accountant.setAccountCode(accountCode);
                statement.setInt(1, accountCode);
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

}
