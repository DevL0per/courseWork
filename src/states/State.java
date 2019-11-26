package states;

import model.university.Faculty;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

public interface State {

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

    void showAllStudentsInGroup(HttpServletRequest request, HttpServletResponse response, Integer groupId) throws IOException;

    void goToStudentProfile(HttpServletRequest request, HttpServletResponse response, Integer profileId) throws IOException;

    void showStudentGradesScreen(HttpServletRequest request, HttpServletResponse response) throws IOException;

    default void removeUserAttributes(HttpServletRequest request, HttpServletResponse response) {
        request.removeAttribute("login");
        request.removeAttribute("password");
        request.removeAttribute("role");
        request.getSession().removeAttribute("login");
        request.getSession().removeAttribute("password");
        request.getSession().removeAttribute("role");
    }
}