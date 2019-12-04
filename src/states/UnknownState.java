package states;

import dao.FacultyDAO;
import dao.GroupDAO;
import model.university.Faculty;
import model.university.Group;
import model.university.Specialty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class UnknownState implements State {

    private final String registrationScreenPath = "/WEB-INF/view/html/registration.jsp";
    List<Group> groups = new CopyOnWriteArrayList<>();
    List<Specialty> specialties = new CopyOnWriteArrayList<>();
    List<Faculty> faculties = new CopyOnWriteArrayList<>();

    FacultyDAO facultyDAO = new FacultyDAO();
    GroupDAO groupDAO = new GroupDAO();

    @Override
    public void showStatistic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void doRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {

        faculties = facultyDAO.getAllWhere("", 0);
        groups = groupDAO.getAllWhere("", 0);

        request.setAttribute("faculties", faculties);
        request.setAttribute("groups", groups);
        try {
            request.getRequestDispatcher(registrationScreenPath).forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void gotoCabinet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/login.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void doDeleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void gotoStudentList(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.sendError(405);
    }

    @Override
    public void addStudentGrades(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void showAllFaculties(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.sendError(405);
    }

    @Override
    public void showAllSpecialtyInFaculty(HttpServletRequest request, HttpServletResponse response, Integer facultyId) throws IOException {
        response.sendError(405);
    }

    @Override
    public void showAllGroupsInSpecialty(HttpServletRequest request, HttpServletResponse response, Integer specialtyId) throws IOException {
        response.sendError(405);
    }

    @Override
    public void showAllStudentsInGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void goToStudentProfile(HttpServletRequest request, HttpServletResponse response, Integer profileId) throws IOException {
        response.sendError(405);
    }

    @Override
    public void editStudentGrades(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void editAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void showStudentGradesScreen(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    @Override
    public void addSpecialty(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void addGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void addFaculty(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void banAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }
}
