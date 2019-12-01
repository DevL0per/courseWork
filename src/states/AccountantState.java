package states;

import dao.UserDAO;
import model.Student;
import model.university.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
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
        HashMap<Integer, Integer> map = new HashMap<>();
        List<Integer> subjectsId = new ArrayList<>();
        List<Integer> grades = new ArrayList<>();

        for(int number = 1; number < 6; number++) {
            String subjectSelector = request.getParameter("gradeSelect" + number);
            if (subjectSelector.equals("-")) {
                continue;
            }
            Integer subjectId = userDAO.getSubjectIdByName(subjectSelector);

            Integer gradeSelect = Integer.valueOf(request.getParameter("grade" + number));
            if (!map.containsKey(subjectId)) {
                map.put(subjectId, gradeSelect);
            }
        }

        Integer numberOfSemester = Integer.valueOf(request.getParameter("numberOfSemester"));

        Integer userId = Integer.valueOf(request.getParameter("studentNumber"));
        String formOfTraining = (request.getParameter("formOfTraining"));

        if (formOfTraining != "платная") {
            calculateScholarship(map, userId);
        }
        try {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    private void calculateScholarship(HashMap<Integer, Integer> map, Integer userId) {
        Double scholarship = 77.08;
        Integer averageMark = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() < 4) {
                scholarship = 0.0;
                break;
            }
            averageMark+=entry.getValue();
        }
        averageMark/=map.size();

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
        Student student = (Student) userDAO.getStudentById("WHERE НомерСтудБилета = ?", id);

        int semester = calculateSemester(student.getStudentNumber());

        request.setAttribute("student", student);
        request.setAttribute("semester", semester);

        try {
            request.getRequestDispatcher("/WEB-INF/view/html/studentGrade.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private int calculateSemester(int studentNumber) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTime(new Date());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH)+1;

        int currentYearNumber = studentNumber/10000000;
        int studentYear = (currentYear - currentYearNumber)/1000;

        int semester;

        switch (studentYear) {
            case 0:
                semester = 1;
                break;
            case 1:
                if (currentMonth>=9) {
                    semester = 3;
                } else {
                    semester = 2;
                }
                break;
            case 2:
                if (currentMonth>=9) {
                    semester = 5;
                } else {
                    semester = 4;
                }
                break;
            case 3:
                if (currentMonth>=9) {
                    semester = 7;
                } else {
                    semester = 6;
                }
                break;
            case 4:
                semester = 8;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + studentYear);
        }
        return semester;
    }
}
