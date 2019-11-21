package states;

import model.university.Faculty;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

public interface State {

    void doRegistration(HttpServletRequest request, HttpServletResponse response);

    void gotoCabinet(HttpServletRequest request, HttpServletResponse response);

    void doLogout(HttpServletRequest request, HttpServletResponse response);

    void doLogin(HttpServletRequest request, HttpServletResponse response);

    void doDeleteUser(HttpServletRequest request, HttpServletResponse response);

    void gotoStudentList(HttpServletRequest request, HttpServletResponse response);

    void calculateScholarship(HttpServletRequest request, HttpServletResponse response);

    void showAllFaculties(HttpServletRequest request, HttpServletResponse response);

    void showAllSpecialtyInFaculty(HttpServletRequest request, HttpServletResponse response, Integer facultyId);

    void showAllGroupsInSpecialty(HttpServletRequest request, HttpServletResponse response, Integer specialtyId);

    default void removeUserAttributes(HttpServletRequest request, HttpServletResponse response) {
        request.removeAttribute("login");
        request.removeAttribute("password");
        request.removeAttribute("role");
        request.getSession().removeAttribute("login");
        request.getSession().removeAttribute("password");
        request.getSession().removeAttribute("role");
    }
}
