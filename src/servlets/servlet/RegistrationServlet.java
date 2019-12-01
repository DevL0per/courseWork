package servlets.servlet;

import dao.UserDAO;
import model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registrationServlet")
public class RegistrationServlet extends HttpServlet {

    private UserDAO userDAO;

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
        final String faculty = req.getParameter("faculty");
        final String group = req.getParameter("group");
        final String number = req.getParameter("number");
        final String email = req.getParameter("email");
        final String password = req.getParameter("password");
        final String formOfTraining = req.getParameter("formOfTraining");

        if (!isRequestNotEmpty(name, surname, patronymic,
                studentNumber, faculty, group, number, email, password)) {
        }

        final Student student = new Student(name, surname, patronymic, number,
                email, password, 1, Integer.valueOf(group),
                Integer.valueOf(studentNumber), 0.0, formOfTraining );

        userDAO = new UserDAO();
        userDAO.create(student);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    private boolean isRequestNotEmpty(String name,String surname,String patronymic,String studentNumber,
                                      String faculty,String group, String number,String email,String password) {
        return isFieldValid(name) && isFieldValid(surname) && isFieldValid(patronymic)
                && isFieldValid(studentNumber) && isFieldValid(faculty) && isFieldValid(number)
                &&  isFieldValid(group) && isFieldValid(email) && isFieldValid(number) && isFieldValid(password);
    }

    private boolean isFieldValid(String value) {
        return value != null && value.length() > 0;
    }

}
