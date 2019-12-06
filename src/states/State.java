package states;


import dao.GroupDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface State {

    GroupDAO groupDao = new GroupDAO();

    void showStatistic(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void doRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void gotoCabinet(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void doDeleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void gotoStudentList(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void addStudentGrades(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void showAllFaculties(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void showAllSpecialtyInFaculty(HttpServletRequest request, HttpServletResponse response, Integer facultyId) throws IOException;

    void showAllGroupsInSpecialty(HttpServletRequest request, HttpServletResponse response, Integer specialtyId) throws IOException;

    void showAllStudentsInGroup(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void editStudentGrades(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void editAccount(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void showStudentGradesScreen(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void addSpecialty(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void addGroup(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void addFaculty(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void banAccount(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void goToStudentProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void addSubject(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    default void removeUserAttributes(HttpServletRequest request, HttpServletResponse response) {
        request.removeAttribute("login");
        request.removeAttribute("password");
        request.removeAttribute("role");
        request.getSession().removeAttribute("login");
        request.getSession().removeAttribute("password");
        request.getSession().removeAttribute("role");
    }

    default void showSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/schedule.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}