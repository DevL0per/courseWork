package states;

import dao.UserDAO;
import model.university.Faculty;
import model.university.Group;
import model.university.Specialty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Objects.nonNull;

public class UnknownState implements State {

    private final String registrationScreenPath = "/WEB-INF/view/html/registration.jsp";
    List<Group> groups = new CopyOnWriteArrayList<>();
    List<Specialty> specialties = new CopyOnWriteArrayList<>();
    List<Faculty> faculties = new CopyOnWriteArrayList<>();

    @Override
    public void doRegistration(HttpServletRequest request, HttpServletResponse response) {

        UserDAO userDAO = new UserDAO();
        faculties = userDAO.getAllFaculty();
        groups = userDAO.getAllGroups();

        request.setAttribute("faculties", faculties);
        request.setAttribute("groups", groups);
        try {
            request.getRequestDispatcher(registrationScreenPath).forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void gotoCabinet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("error");
    }

    @Override
    public void doLogout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("error");
    }

    @Override
    public void doLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/login.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
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

        if (!nonNull(request.getAttribute("faculties"))) {
            UserDAO userDAO = new UserDAO();
            faculties = userDAO.getAllFaculty();
            request.setAttribute("faculties", faculties);
        }
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/listOfFaculty.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void showAllSpecialtyInFaculty(HttpServletRequest request, HttpServletResponse response, Integer facultyId) {
        if (!nonNull(request.getAttribute("specialties"))) {
            UserDAO userDAO = new UserDAO();
            specialties = userDAO.getAllSpecialtyInFaculty(facultyId);
            request.setAttribute("specialties", specialties);
        }
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/listOfSpecialities.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void showAllGroupsInSpecialty(HttpServletRequest request, HttpServletResponse response, Integer specialtyId) {
        if (!nonNull(request.getAttribute("groups"))) {
            UserDAO userDAO = new UserDAO();
            groups = userDAO.getAllGroupInSpecialty(specialtyId);
            request.setAttribute("groups", groups);
        }
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/listOfGroups.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
