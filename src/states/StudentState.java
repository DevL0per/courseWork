package states;

import dao.UserDAO;
import model.Student;
import model.university.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Objects.nonNull;

public class StudentState implements State {

    UserDAO userDAO = new UserDAO();
    List<Group> groups = new CopyOnWriteArrayList<>();
    List<Specialty> specialties = new CopyOnWriteArrayList<>();
    List<Faculty> faculties = new CopyOnWriteArrayList<>();
    List<Student> students = new CopyOnWriteArrayList<>();
    List<StudentProgress> studentProgresses = new CopyOnWriteArrayList<>();
    List<Subject> subjects = new CopyOnWriteArrayList<>();

    @Override
    public void doRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void gotoCabinet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String login = (String) request.getSession().getAttribute("login");
        String password = (String) request.getSession().getAttribute("password");

        Integer id = userDAO.getIdByAccountIdByLoginAndPassword(login, password);

        Student student = (Student) userDAO.getStudentById("WHERE УчетнаяЗапись_КодУчетнойЗаписи = ?", id);
        Group group = userDAO.getGroupById(student.getNumberOfGroup());
        Specialty specialty = userDAO.getSpecialtyById(group.getSpecialtyId());
        studentProgresses = userDAO.getStudentProgressById(student.getStudentNumber());
        if (studentProgresses.isEmpty()) {
            request.setAttribute("subjects", null);
        } else {
            for (int number = 0; number < studentProgresses.size(); number++) {
                Integer subjectId = studentProgresses.get(number).getNumberOfSubject();
                Subject subject = userDAO.getSubjectBy( " WHERE КодПредмета = ?", subjectId);
                subjects.add(subject);
            }
            request.setAttribute("subjects", subjects);
            request.setAttribute("studentProgresses", studentProgresses);
        }

        request.setAttribute("specialty", specialty);
        request.setAttribute("group", group);
        request.setAttribute("student", student);
        request.setAttribute("scholarship", student.getScholarship());

        try {
            request.getRequestDispatcher("/WEB-INF/view/html/studentAccount.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }

    }

    @Override
    public void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        removeUserAttributes(request, response);
        try {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void doDeleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void gotoStudentList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void addStudentGrades(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void showAllFaculties(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
    public void showAllSpecialtyInFaculty(HttpServletRequest request, HttpServletResponse response, Integer facultyId) throws IOException {
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
    public void showAllGroupsInSpecialty(HttpServletRequest request, HttpServletResponse response, Integer specialtyId) throws IOException {
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

    @Override
    public void showAllStudentsInGroup(HttpServletRequest request, HttpServletResponse response, Integer groupId) throws IOException {
        if (!nonNull(request.getAttribute("students"))) {
            students = userDAO.getAllStudentsWhere("WHERE Группа_НомерГруппы = ?", groupId);
            request.setAttribute("students", students);
        }
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/listOfStudents.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void goToStudentProfile(HttpServletRequest request, HttpServletResponse response, Integer profileId) throws IOException {
        response.sendError(405);
    }

    @Override
    public void showStudentGradesScreen(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

}
