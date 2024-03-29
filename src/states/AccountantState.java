package states;

import dao.*;
import model.Role;
import model.Student;
import model.university.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Objects.nonNull;

public class AccountantState implements State {

    AccountDAO accountDAO = new AccountDAO();
    SubjectDAO subjectDAO = new SubjectDAO();
    StudentDao studentDao = new StudentDao();
    GroupDAO groupDAO = new GroupDAO();
    FacultyDAO facultyDAO = new FacultyDAO();
    SpecialtyDAO specialtyDAO = new SpecialtyDAO();
    StudentProgressDAO studentProgressDAO = new StudentProgressDAO();

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
        response.sendError(405);
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

        if (!formOfTraining.equals("платная")) {
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
        scholarship = new BigDecimal(scholarship).setScale(2, RoundingMode.UP).doubleValue();
        studentDao.addStudentScholarshipById(userId, scholarship);
    }


    @Override
    public void showAllFaculties(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Faculty> faculties = facultyDAO.getAllWhere("", 0);
        request.setAttribute("faculties", faculties);
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/listOfFaculty.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void showAllSpecialtyInFaculty(HttpServletRequest request, HttpServletResponse response, Integer facultyId) throws IOException {
        if (!nonNull(request.getAttribute("specialties"))) {
            List<Specialty> specialties = specialtyDAO.getAllSpecialtyInFaculty(facultyId);
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
            List<Group> groups = groupDAO.getAllGroupInSpecialty(specialtyId);
            request.setAttribute("groups", groups);
        }
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/listOfGroups.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void showAllStudentsInGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Integer groupId = Integer.valueOf(request.getParameter("group"));
        request.setAttribute("group", groupId);

        if(request.getParameter("searchParameter") != null) {
            String parameter = request.getParameter("searchParameter");
            List<Student> students = new CopyOnWriteArrayList<>();
            List<Integer> list = null;
            String[] parameters = new String[] {"Имя", "Фамилия", "Отчество", "Телефон"};

            for (int number = 0; number < parameters.length; number++) {
                list = accountDAO.getAllWhere("WHERE " + parameters[number] + " LIKE ?", parameter);
                if (!list.isEmpty()) {
                    break;
                }
            }
            if (!list.isEmpty()) {
                for (int number = 0; number < list.size(); number++) {
                    Integer accountId = list.get(number);
                    Student student = (Student) studentDao.getAllWhere("WHERE УчетнаяЗапись_КодУчетнойЗаписи = ?",
                            accountId).get(0);
                    if (student.getNumberOfGroup().equals(groupId)) {
                        students.add(student);
                    }
                }
            }

            request.setAttribute("students", students);
            try {
                request.getRequestDispatcher("/WEB-INF/view/html/accountantListOfStudents.jsp").forward(request, response);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        } else {
            List<Student> students = studentDao.getAllWhere("WHERE Группа_НомерГруппы = ?", groupId);
            request.setAttribute("students", students);
            try {
                request.getRequestDispatcher("/WEB-INF/view/html/accountantListOfStudents.jsp").forward(request, response);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    @Override
    public void goToStudentProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer studentId = Integer.valueOf(request.getParameter("studentId"));
        Student student = (Student) studentDao.getEntityById(studentId);
        request.setAttribute("student", student);
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/studentProfile.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void editStudentGrades(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean flag = false;
        if (request.getParameter("gradeId") != null) {
            flag = editStudentGrade(request);
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

    private boolean editStudentGrade(HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getParameter("gradeId"));
        Integer grade = null;
        try {
            grade = Integer.valueOf(request.getParameter("value"));
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Некорректный ввод");
            return false;
        }
        studentProgressDAO.update(id, grade, "Оценка");
        return true;
    }

    @Override
    public void editAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
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
        Integer facultyId = Integer.valueOf(request.getParameter("facultyId"));
        String newSpecialtyName = request.getParameter("newSpecialty");

        if (newSpecialtyName != null && checkNewSpecialty(newSpecialtyName, facultyId, request)) {
            newSpecialtyName = newSpecialtyName.trim();
            newSpecialtyName = newSpecialtyName.toUpperCase();
            Specialty specialty = new Specialty(0, newSpecialtyName, facultyId);
            specialtyDAO.create(specialty);
            showAllSpecialtyInFaculty(request, response, facultyId);

        } else {
            request.setAttribute("facultyId", facultyId);
            try {
                request.getRequestDispatcher("/WEB-INF/view/html/addSpecialty.jsp").forward(request, response);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    private boolean checkNewSpecialty(String newSpecialtyName, Integer facultyId, HttpServletRequest request) {
        if (newSpecialtyName == null) {
            request.setAttribute("errorMessage", "Некорректный ввод");
            return false;
        }
        newSpecialtyName = newSpecialtyName.trim();
        newSpecialtyName = newSpecialtyName.toUpperCase();
        if (newSpecialtyName.isEmpty()) {
            request.setAttribute("errorMessage", "Некорректный ввод");
            return false;
        }
        List<Specialty> specialties = specialtyDAO.getAllSpecialtyInFaculty(facultyId);
        for (Specialty specialty : specialties) {
            if (specialty.getName().equals(newSpecialtyName)) {
                request.setAttribute("errorMessage", "Специальность уже существует");
                return false;
            }
        }
        return true;
    }

    @Override
    public void addGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Integer specialtyId = Integer.valueOf(request.getParameter("specialtyId"));
        String newGroupName = request.getParameter("newGroup");
        String newGroupCourse = request.getParameter("newGroupCourse");

        if (newGroupName != null && checkNewGroup(newGroupName, newGroupCourse, specialtyId,request)) {

            Integer newGroupNameInt = Integer.valueOf(request.getParameter("newGroup"));
            Integer newGroupCourseInt = Integer.valueOf(request.getParameter("newGroupCourse"));

            Group group = new Group(newGroupNameInt, newGroupCourseInt, specialtyId);
            groupDAO.create(group);
            showAllGroupsInSpecialty(request, response, specialtyId);
        } else {
            request.setAttribute("specialtyId", specialtyId);
            try {
                request.getRequestDispatcher("/WEB-INF/view/html/addGroup.jsp").forward(request, response);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    private boolean checkNewGroup(String newGroupName, String newGroupCourse,
                                 Integer specialtyId, HttpServletRequest request) {
        if (newGroupName == null || newGroupCourse == null ) {
            request.setAttribute("errorMessage", "Некорректный ввод");
            return false;
        }
        newGroupName = newGroupName.trim();
        newGroupCourse = newGroupCourse.trim();
        if (newGroupName.length() != 6 || newGroupCourse.length() == 0) {
            request.setAttribute("errorMessage", "Некорректный ввод");
            return false;
        }
        Integer newGroupNameInteger;
        try {
            newGroupNameInteger = Integer.valueOf(newGroupName);
            Integer newGroupCourseInteger = Integer.valueOf(newGroupCourse);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Некорректный ввод");
            return false;
        }
        List<Group> groups = groupDAO.getAllGroupInSpecialty(specialtyId);
        for (int number = 0; number < groups.size(); number++) {
            if (groups.get(number).getNumberOfGroup().equals(newGroupNameInteger)) {
                request.setAttribute("errorMessage", "Группа уже существует");
                return false;
            }
        }
        return true;
    }

    @Override
    public void addSubject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String newSubjectName = req.getParameter("newSubjectName");

        if(newSubjectName != null && checkNewSubject(req, newSubjectName)) {
            newSubjectName = newSubjectName.trim();
            newSubjectName = newSubjectName.substring(0,1).toUpperCase() + newSubjectName.substring(1);
            Subject subject = new Subject(0, newSubjectName);
            subjectDAO.create(subject);
            showAllFaculties(req, resp);
        } else {
            try {
                req.getRequestDispatcher("/WEB-INF/view/html/addSubject.jsp").forward(req, resp);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }

    }

    private boolean checkNewSubject(HttpServletRequest req, String newSubjectName ) {
        newSubjectName = newSubjectName.trim();
        if (newSubjectName.isEmpty()) {
            req.setAttribute("errorMessage", "Некорректный ввод");
            return false;
        }
        newSubjectName = newSubjectName.substring(0,1).toUpperCase() + newSubjectName.substring(1);
        List<Subject> subjectList = subjectDAO.getAllWhere("", 0);
        for (Subject subject : subjectList) {
            if (subject.getName().equals(newSubjectName)) {
                req.setAttribute("errorMessage", "Предмет уже существует");
                return false;
            }
        }
        return true;
    }

    @Override
    public void addFaculty(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String newFacultyName = request.getParameter("newFaculty");

        if (newFacultyName != null && checkNewFaculty(newFacultyName, request)) {
            newFacultyName = newFacultyName.toUpperCase();
            newFacultyName = newFacultyName.trim();
            Faculty faculty = new Faculty(0, newFacultyName);
            showAllFaculties(request, response);
            facultyDAO.create(faculty);
        } else {
            try {
                request.getRequestDispatcher("/WEB-INF/view/html/addFaculty.jsp").forward(request, response);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    private boolean checkNewFaculty(String newFacultyName, HttpServletRequest request) {
        if (newFacultyName == null) {
            request.setAttribute("errorMessage", "Некорректный ввод");
            return false;
        }
        newFacultyName = newFacultyName.toUpperCase();
        newFacultyName = newFacultyName.trim();
        if (newFacultyName.isEmpty()) {
            request.setAttribute("errorMessage", "Некорректный ввод");
            return false;
        }
        List<Faculty> faculties = facultyDAO.getAllWhere("", 0);
        for (Faculty faculty : faculties) {
            if (faculty.getName().equals(newFacultyName)) {
                request.setAttribute("errorMessage", "Факультет уже существует");
                return false;
            }
        }
        return true;
    }

    @Override
    public void banAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer studentNumber = Integer.valueOf(request.getParameter("studentId"));

        Student student = (Student) studentDao.getEntityById(studentNumber);
        if (student.getRole().equals(Role.STUDENT)) {
            accountDAO.update(student.getAccountCode(), "заблокирован", "Роль");
        } else {
            accountDAO.update(student.getAccountCode(), "студент", "Роль");
        }
        showAllStudentsInGroup(request, response);
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
