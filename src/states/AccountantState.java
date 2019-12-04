package states;

import dao.*;
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

    SubjectDAO subjectDAO = new SubjectDAO();
    StudentDao studentDao = new StudentDao();
    GroupDAO groupDAO = new GroupDAO();
    FacultyDAO facultyDAO = new FacultyDAO();
    SpecialtyDAO specialtyDAO = new SpecialtyDAO();
    StudentProgressDAO studentProgressDAO = new StudentProgressDAO();

    List<Group> groups = new CopyOnWriteArrayList<>();
    List<Specialty> specialties = new CopyOnWriteArrayList<>();
    List<Faculty> faculties = new CopyOnWriteArrayList<>();
    List<Student> students = new CopyOnWriteArrayList<>();

    @Override
    public void showStatistic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

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

        Integer numberOfSemester = Integer.valueOf(request.getParameter("numberOfSemester"));
        Integer semester = calculateSemester(numberOfSemester);
        Integer userId = Integer.valueOf(request.getParameter("studentNumber"));
        String formOfTraining = (request.getParameter("formOfTraining"));

        for(int number = 1; number < 6; number++) {
            String subjectSelector = request.getParameter("gradeSelect" + number);
            if (subjectSelector.equals("-")) {
                continue;
            }
            Integer subjectId = subjectDAO.getSubjectIdByName(subjectSelector);

            Integer gradeSelect = Integer.valueOf(request.getParameter("grade" + number));
            if (!map.containsKey(subjectId)) {
                map.put(subjectId, gradeSelect);
                StudentProgress studentProgress = new StudentProgress(0, gradeSelect,
                        subjectId, userId, semester);
                studentProgressDAO.create(studentProgress);
            }
        }

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
        Double averageMark = 0.0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() < 4) {
                scholarship = 0.0;
                return;
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
        studentDao.addStudentScholarshipById(userId, scholarship);
    }


    @Override
    public void showAllFaculties(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!nonNull(request.getAttribute("faculties"))) {
            UserDAO userDAO = new UserDAO();
            faculties = facultyDAO.getAllWhere("", 0);
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
            specialties = specialtyDAO.getAllSpecialtyInFaculty(facultyId);
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
            groups = groupDAO.getAllGroupInSpecialty(specialtyId);
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
            students = studentDao.getAllWhere("WHERE Группа_НомерГруппы = ?", groupId);
            request.setAttribute("students", students);
        }
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/accountantListOfStudents.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void goToStudentProfile(HttpServletRequest request, HttpServletResponse response, Integer profileId) throws IOException {

    }

    @Override
    public void editStudentGrades(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean flag = false;
        if (request.getParameter("gradeId") != null) {
            Integer id = Integer.valueOf(request.getParameter("gradeId"));
            Integer value = Integer.valueOf(request.getParameter("value"));
            studentProgressDAO.update(id, value, "Оценка");
            flag = true;
        }

        Integer studentId;

        if (request.getParameter("studentId") == null) {
            studentId = (Integer) request.getSession().getAttribute("studentId");
        } else {
            studentId = Integer.valueOf(request.getParameter("studentId"));
            request.getSession().setAttribute("studentId", studentId);
        }

        HashMap<Integer, Integer> gradesAndSubjectsMap = new HashMap<>();
        List<StudentProgress> list = studentProgressDAO.getAllWhere("WHERE Студент_НомерСтудБилета = ?", studentId);
        SortedMap<Subject, StudentProgress> studentProgressMap = new TreeMap<Subject, StudentProgress>();

        int numberOfSemester = 1;
        for (int number = 0; number < list.size(); number++) {
            int listSemester = list.get(number).getNumberOfSemester();
            if (numberOfSemester < listSemester) {
                numberOfSemester = listSemester;
            }
        }

        for (int number = 0; number < list.size(); number++) {
            int listSemester = list.get(number).getNumberOfSemester();
            if (listSemester == numberOfSemester) {
                StudentProgress studentProgress = list.get(number);
                Subject subject = (Subject) subjectDAO.getEntityById(studentProgress.getNumberOfSubject());
                gradesAndSubjectsMap.put(subject.getNumberOfSubject(), studentProgress.getGrade());
                studentProgressMap.put(subject, studentProgress);
            }
        }
        request.setAttribute("lastGrades", studentProgressMap);
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/editStudentGrades.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        if (flag) {
            calculateScholarship(gradesAndSubjectsMap, studentId);
        }
    }

    @Override
    public void editAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    @Override
    public void showStudentGradesScreen(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Subject> subjects = new CopyOnWriteArrayList<>();
        subjects = subjectDAO.getAllWhere("", 0);
        request.setAttribute("subjects", subjects);

        Integer id = Integer.valueOf(request.getParameter("studentId"));
        Student student = (Student) studentDao.getEntityById(id);

        int semester = calculateSemester(student.getStudentNumber());

        List<StudentProgress> grades = studentProgressDAO.getAllWhere("WHERE Студент_НомерСтудБилета = ?", student.getStudentNumber());
        for(int number = 0; number < grades.size(); number++) {
            if (grades.get(number).getNumberOfSemester() == semester) {
                request.setAttribute("error", "Оценки уже были выставлены");
            }
        }

        request.setAttribute("student", student);
        request.setAttribute("semester", semester);

        try {
            request.getRequestDispatcher("/WEB-INF/view/html/studentGrade.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void addSpecialty(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("newSpecialty") != null) {
            String newSpecialtyName = request.getParameter("newSpecialty");

            Integer facultyId = Integer.valueOf(request.getParameter("facultyId"));
            Specialty specialty = new Specialty(0, newSpecialtyName, facultyId);
            specialtyDAO.create(specialty);
        } else {
            Integer id = Integer.valueOf(request.getParameter("facultyId"));
            request.setAttribute("facultyId", id);
            try {
                request.getRequestDispatcher("/WEB-INF/view/html/addSpecialty.jsp").forward(request, response);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    @Override
    public void addGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("newGroup") != null) {
            Integer newGroupName = Integer.valueOf(request.getParameter("newGroup"));
            Integer newGroupCourse = Integer.valueOf(request.getParameter("newGroupCourse"));

            Integer specialtyId = Integer.valueOf(request.getParameter("specialtyId"));

            Group group = new Group(newGroupName, newGroupCourse, specialtyId);
            groupDAO.create(group);
        } else {
            Integer id = Integer.valueOf(request.getParameter("specialtyId"));
            request.setAttribute("specialtyId", id);
            try {
                request.getRequestDispatcher("/WEB-INF/view/html/addGroup.jsp").forward(request, response);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    @Override
    public void addFaculty(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("newFaculty") != null) {
            String newFacultyName = request.getParameter("newFaculty");

            Faculty faculty = new Faculty(0, newFacultyName);
            facultyDAO.create(faculty);
        } else {
            try {
                request.getRequestDispatcher("/WEB-INF/view/html/addFaculty.jsp").forward(request, response);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
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
