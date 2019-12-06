package servlets.servlet;

import dao.AccountDAO;
import dao.GroupDAO;
import dao.StudentDao;
import model.Role;
import model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/registrationServlet")
public class RegistrationServlet extends HttpServlet {

    private StudentDao studentDao = new StudentDao();
    private AccountDAO accountDAO = new AccountDAO();
    private GroupDAO groupDAO;

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF8");

        final String name = req.getParameter("name");
        final String surname = req.getParameter("surname");
        final String patronymic = req.getParameter("patronymic");
        final String studentNumber = req.getParameter("studentNumber");
        final String group = req.getParameter("group");
        final String number = req.getParameter("number");
        final String email = req.getParameter("email");
        final String formOfTraining = req.getParameter("formOfTraining");
        String password = req.getParameter("password");

        String message = validateAndReturnMistakeMessage(name, surname, patronymic,
                studentNumber, group, number, email, password);
        password = password.trim();
        if(message != null) {
            groupDAO = new GroupDAO();
            List groups = groupDAO.getAllWhere("", 0);
            req.setAttribute("groups", groups);
            req.setAttribute("errorMessage", message);
            req.getRequestDispatcher("/WEB-INF/view/html/registration.jsp").forward(req, resp);
            return;
        }

        final Student student = new Student(name, surname, patronymic, number,
                email, password, 1, Integer.parseInt(group.trim()),
                Integer.parseInt(studentNumber.trim()), 0.0, formOfTraining, Role.STUDENT);

        studentDao = new StudentDao();
        studentDao.create(student);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    private String validateAndReturnMistakeMessage(String name,String surname,String patronymic,String studentNumber,String group,
                                                   String number,String email,String password) {
        if (accountDAO.isUserExist(email, password)) {
            return "Пользователь с таким email уже существует";
        }
        if (!((isFieldValid(name) && name.length() > 3))) {
            return "Имя заполнено некорректно";
        } if (!((isFieldValid(surname) && surname.length() > 3))) {
            return "Фамилия заполнена некорректно";
        } if (!((isFieldValid(patronymic) && patronymic.length() > 3))) {
            return "Отчество заполнена некорректно";
        }
        if (studentNumber.length() != 8) {
            return "Номер студенческого заполнен некорректно";
        }
        Integer studentNumberInt;
        try {
            studentNumberInt = Integer.valueOf(studentNumber);
        } catch (Exception e) {
            return "Номер студенческого заполнен некорректно";
        }
        if (!((isFieldValid(number) && number.length() >= 10))) {
            return "Номер телона заполнен некорректно";
        }
        Student student = (Student) studentDao.getEntityById(studentNumberInt);
        if (student != null) {
            return "Студент с таким номером студенческого уже существует";
        } if (!((isFieldValid(email) && number.length() >= 5) && email.contains("@"))) {
            return "Почта заполнена некорректно";
        } if (!((isFieldValid(password) && password.length() >= 8))) {
            return "пароль слишком короткий";
        } if (password.length() > 20) {
            return "пароль слишком длинный";
        }
        return null;
    }

    private boolean isRequestNotEmpty(String name,String surname,String patronymic,String studentNumber,
                                      String group, String number,String email,String password) {
        return isFieldValid(name) && isFieldValid(surname) && isFieldValid(patronymic)
                && isFieldValid(studentNumber) && isFieldValid(number)
                &&  isFieldValid(group) && isFieldValid(email) && isFieldValid(number) && isFieldValid(password);
    }

    private boolean isFieldValid(String value) {
        return value != null && value.length() > 0;
    }

}
