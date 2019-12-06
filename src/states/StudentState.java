package states;

import dao.*;
import model.Student;
import model.university.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static java.util.Objects.nonNull;

public class StudentState implements State {

    SubjectDAO subjectDAO = new SubjectDAO();
    StudentDao studentDao = new StudentDao();
    GroupDAO groupDAO = new GroupDAO();
    FacultyDAO facultyDAO = new FacultyDAO();
    SpecialtyDAO specialtyDAO = new SpecialtyDAO();
    AccountDAO accountDAO = new AccountDAO();
    StudentProgressDAO studentProgressesDao = new StudentProgressDAO();

    @Override
    public void showStatistic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer studentId = Integer.valueOf(request.getParameter("studentId"));
        List<StudentProgress> grades =  studentProgressesDao.getAllWhere("WHERE Студент_НомерСтудБилета = ?", studentId);

        SortedMap<Integer, Integer> gradesPerSemester = new TreeMap<Integer, Integer>();

        for (int number = 0; number < grades.size(); number++) {
            int semester = grades.get(number).getNumberOfSemester();
            gradesPerSemester.put(semester, 0);
        }

        for (Integer semester : gradesPerSemester.keySet()) {
            int averageMark = 0;
            int counter = 0;
            for (StudentProgress progress : grades) {
                if (progress.getNumberOfSemester().equals(semester)) {
                    averageMark += progress.getGrade();
                    counter += 1;
                }
            }
            averageMark/=counter;
            gradesPerSemester.put(semester, averageMark);
        }

        request.setAttribute("gradesPerSemester", gradesPerSemester);
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/chart.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void doRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void gotoCabinet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String login = (String) request.getSession().getAttribute("login");
        String password = (String) request.getSession().getAttribute("password");

        SortedMap<StudentProgress, Subject> studentProgressMap = new TreeMap<StudentProgress, Subject>();

        Integer id = accountDAO.getIdByAccountIdByLoginAndPassword(login, password);

        Student student = ((List<Student>) studentDao.getAllWhere("WHERE УчетнаяЗапись_КодУчетнойЗаписи = ?", id)).get(0);

        Group group = (Group) groupDAO.getEntityById(student.getNumberOfGroup());
        Specialty specialty = (Specialty) specialtyDAO.getEntityById(group.getSpecialtyId());

        List<StudentProgress> studentProgresses = studentProgressesDao.getAllWhere( "WHERE Студент_НомерСтудБилета = ?", student.getStudentNumber());
        sortStudentProgressBySemester(studentProgresses);

        if (studentProgresses.isEmpty()) {
            request.setAttribute("studentProgresses", null);
        } else {
            for (StudentProgress studentProgress : studentProgresses) {
                Integer subjectId = studentProgress.getNumberOfSubject();
                Subject subject = (Subject) subjectDAO.getEntityById(subjectId);
                studentProgressMap.put(studentProgress, subject);
            }
            request.setAttribute("studentProgresses", studentProgressMap);
            double averageBallToNextScholarship = calculateAverageBallToNextScholarship(studentProgresses);
            request.setAttribute("averageBallToNextScholarship", averageBallToNextScholarship);
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

    private double calculateAverageBallToNextScholarship(List<StudentProgress> progresses) {
        int lastSemester =  progresses.get(progresses.size()-1).getNumberOfSemester();

        int counter = 0;
        double averageBall = 0;
        for (int number = 0; number < progresses.size(); number++) {
            StudentProgress progress = progresses.get(number);
            if (progress.getNumberOfSemester() == lastSemester) {
                counter+=1;
                averageBall+=progress.getGrade();
            }
        }
        averageBall/=counter;

        double finalBall = 0;

        if (averageBall < 5) {
            finalBall = 5-averageBall;
        } else if (averageBall < 6) {
            finalBall = 6 - averageBall;
        } else if (averageBall < 8) {
            finalBall = 8-averageBall;
        } else  if (averageBall < 9) {
            finalBall = 9-averageBall;
        }

        finalBall = new BigDecimal(finalBall).setScale(2, RoundingMode.UP).doubleValue();
        return finalBall;
    }

    public void sortStudentProgressBySemester(List<StudentProgress> studentProgresses) {
        Collections.sort(studentProgresses, new Comparator<StudentProgress>() {
            @Override
            public int compare(StudentProgress t0, StudentProgress t1) {
                if (t0.getNumberOfSemester() < t1.getNumberOfSemester()) {
                    return -1;
                } else if (t0.getNumberOfSemester().equals(t1.getNumberOfSemester())) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
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
            List<Faculty> faculties = facultyDAO.getAllWhere("", 0);
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
        if (!nonNull(request.getAttribute("students"))) {
            List<Student> students = studentDao.getAllWhere("WHERE Группа_НомерГруппы = ?", groupId);
            request.setAttribute("students", students);
        }
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/listOfStudents.jsp").forward(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void goToStudentProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void addSubject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(405);
    }

    @Override
    public void editStudentGrades(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
    }

    @Override
    public void editAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getParameter("newName") != null) {
            Student student = (Student) request.getSession().getAttribute("student");
            editStudent(request, response, student);
            try {
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        } else {
            Integer studentId = Integer.valueOf(request.getParameter("studentId"));
            Student student = (Student) studentDao.getEntityById(studentId);
            List groups = groupDAO.getAllWhere("", 0);

            request.setAttribute("groups", groups);
            request.setAttribute("student", student);
            request.getSession().setAttribute("student", student);
            try {
                request.getRequestDispatcher("/WEB-INF/view/html/editAccount.jsp").forward(request, response);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    private void editStudent(HttpServletRequest request, HttpServletResponse response, Student student) {
        String newName = request.getParameter("newName");
        String newSurname = request.getParameter("newSurname");
        String newPatronymic = request.getParameter("newPatronymic");
        String newPhoneNumber = request.getParameter("newPhoneNumber");
        String newEmail = request.getParameter("newEmail");
        String newPassword = request.getParameter("newPassword");
        Integer newGroup = Integer.valueOf(request.getParameter("group"));

        int id = student.getStudentNumber();
        int accountId = student.getAccountCode();

        if (!newName.equals(student.getName()) && !newName.isEmpty()) {
            accountDAO.update(accountId, newName, "Имя");
        }
        if (!newSurname.equals(student.getSurname()) && !newSurname.isEmpty()) {
            accountDAO.update(accountId, newSurname, "Фамилия");
        }
        if (!newPatronymic.equals(student.getPatronymic()) && !newPatronymic.isEmpty()) {
            accountDAO.update(accountId, newPatronymic, "Отчество");
        }
        newPhoneNumber = newPhoneNumber.trim();
        if (!newPhoneNumber.equals(student.getPhoneNumber()) && !newPhoneNumber.isEmpty()
                && newPhoneNumber.length() > 9) {
            accountDAO.update(accountId, newPhoneNumber, "Телефон");
        }
        newEmail = newEmail.trim();
        if (!newEmail.equals(student.getEmail()) && !newEmail.isEmpty()) {
            accountDAO.update(accountId, newEmail, "Почта");
        }
        newPassword = newPassword.trim();
        if (!newPassword.equals(student.getPassword()) && newPassword.length() > 7) {
            accountDAO.update(accountId, newPassword, "Пароль");
        }
        if (!newGroup.equals(student.getNumberOfGroup())) {
            studentDao.update(student.getStudentNumber(), newGroup, "Группа_НомерГруппы");
        }
    }

    @Override
    public void showStudentGradesScreen(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405);
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
