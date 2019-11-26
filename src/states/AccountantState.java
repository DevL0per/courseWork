package states;

import dao.UserDAO;
import model.Student;
import model.university.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Objects.nonNull;

public class AccountantState implements State {

    UserDAO userDAO = new UserDAO();
    List<Group> groups = new CopyOnWriteArrayList<>();
    List<Specialty> specialties = new CopyOnWriteArrayList<>();
    List<Faculty> faculties = new CopyOnWriteArrayList<>();
    List<Student> students = new CopyOnWriteArrayList<>();

    @Override
    public void doRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void gotoCabinet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
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
    }

    @Override
    public void addStudentGrades(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Integer> subjectsId = new ArrayList<>();
        List<Integer> grades = new ArrayList<>();

        for(int number = 1; number < 6; number++) {
            String gradeSelect = request.getParameter("gradeSelect" + number);
            Integer subjectId = userDAO.getSubjectIdByName(gradeSelect);
            subjectsId.add(subjectId);
        }

        for(int number = 1; number < 6; number++) {
            Integer gradeSelect = Integer.valueOf(request.getParameter("grade" + number));
            grades.add(gradeSelect);
        }

        Integer userId = Integer.valueOf(request.getParameter("studentNumber"));

        for(int number = 1; number < subjectsId.size(); number++) {
            Integer grade = grades.get(number);
            Integer subjectId = subjectsId.get(number);
            StudentProgress studentProgress = new StudentProgress(0, grade, subjectId, userId);
            userDAO.addGrades(studentProgress);
        }
    }


    private void calculateScholarship(Integer userId, List<Integer> grades) {
        Double scholarship = 77.08;
        Integer averageMark = 0;
        for (int number = 0; number < grades.size(); number++) {
            averageMark+=grades.get(number);
        }
        averageMark/=grades.size();

        if (averageMark<5) {
            scholarship = 0.0;
        } else if (averageMark>=5 && averageMark<=5.99) {
            scholarship*=1;
        } else if (averageMark>=6 && averageMark<=7.99) {
            scholarship*=1.2;
        } else if (averageMark>=8 && averageMark<=8.99) {
            scholarship*=1.4;
        } else if (averageMark>=9 && averageMark<=10) {
            scholarship*=1.6;
        }
        userDAO.addStudentScholarshipById(userId, scholarship);
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

    }

    @Override
    public void showStudentGradesScreen(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Subject> subjects = new CopyOnWriteArrayList<>();
        subjects = userDAO.getAllSubjects();
        request.setAttribute("subjects", subjects);

        Integer id = Integer.valueOf(request.getParameter("studentId"));
        Student student = (Student) userDAO.getStudentById(id);
        request.setAttribute("student", student);

        try {
            request.getRequestDispatcher("/WEB-INF/view/html/studentGrade.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
