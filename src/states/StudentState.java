package states;

import dao.UserDAO;
import model.Student;
import model.university.Faculty;
import model.university.Group;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Objects.nonNull;

public class StudentState implements State {

    UserDAO userDAO = new UserDAO();

    @Override
    public void doRegistration(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("error");
    }

    @Override
    public void gotoCabinet(HttpServletRequest request, HttpServletResponse response) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        Student student = (Student) userDAO.getStudentById(id);
        request.getSession().setAttribute("студент", student);
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/studentMenu.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }

    }

    @Override
    public void doLogout(HttpServletRequest request, HttpServletResponse response) {
        removeUserAttributes(request, response);
        try {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void doLogin(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("error");
    }

    @Override
    public void doDeleteUser(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("error");
    }

    @Override
    public void gotoStudentList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("error");
    }

    @Override
    public void calculateScholarship(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("error");
    }

    @Override
    public void showAllFaculties(HttpServletRequest request, HttpServletResponse response) {
        if (!(nonNull(request.getSession()) && nonNull(request.getSession().getAttribute("faculties")))) {
            List<Faculty> faculties = new CopyOnWriteArrayList<>();
            faculties = userDAO.getAllFaculty();
            request.getSession().setAttribute("faculties", faculties);
        }
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/listOfFaculty.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void showAllSpecialtyInFaculty(HttpServletRequest request, HttpServletResponse response, Integer facultyId) {

    }

    @Override
    public void showAllGroupsInSpecialty(HttpServletRequest request, HttpServletResponse response, Integer specialtyId) {

    }
}
