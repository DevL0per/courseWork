package states;

import dao.*;
import model.Student;
import model.university.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Objects.nonNull;

public class StudentState implements State {

    SubjectDAO subjectDAO = new SubjectDAO();
    StudentDao studentDao = new StudentDao();
    GroupDAO groupDAO = new GroupDAO();
    FacultyDAO facultyDAO = new FacultyDAO();
    SpecialtyDAO specialtyDAO = new SpecialtyDAO();
    AccountDAO accountDAO = new AccountDAO();
    StudentProgressDAO studentProgressesDao = new StudentProgressDAO();

    List<Group> groups = new CopyOnWriteArrayList<>();
    List<Specialty> specialties = new CopyOnWriteArrayList<>();
    List<Faculty> faculties = new CopyOnWriteArrayList<>();
    List<Student> students = new CopyOnWriteArrayList<>();
    List<StudentProgress> studentProgresses = new CopyOnWriteArrayList<>();
    List<Subject> subjects = new CopyOnWriteArrayList<>();

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
            for (int number = 0; number < grades.size(); number++) {
                StudentProgress progress = grades.get(number);
                if (progress.getNumberOfSemester() == semester) {
                    averageMark+=progress.getGrade();
                    counter+=1;
                }
            }
            averageMark/=counter;
            gradesPerSemester.put(semester, averageMark);
        }

        request.setAttribute("gradesPerSemester", gradesPerSemester);
        try {
            request.getRequestDispatcher("/WEB-INF/view/html/schedule.jsp").forward(request, response);
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

        Integer id = accountDAO.getIdByAccountIdByLoginAndPassword(login, password);

        Student student = ((List<Student>) studentDao.getAllWhere("WHERE УчетнаяЗапись_КодУчетнойЗаписи = ?", id)).get(0);
        Group group = (Group) groupDAO.getEntityById(student.getNumberOfGroup());
        Specialty specialty = (Specialty) specialtyDAO.getEntityById(group.getSpecialtyId());

        studentProgresses = studentProgressesDao.getAllWhere( "WHERE Студент_НомерСтудБилета = ?", student.getStudentNumber());
        sortStudentProgressBySemester(studentProgresses);

        if (studentProgresses.isEmpty()) {
            request.setAttribute("subjects", null);
        } else {
            for (int number = 0; number < studentProgresses.size(); number++) {
                Integer subjectId = studentProgresses.get(number).getNumberOfSubject();
                Subject subject = (Subject) subjectDAO.getEntityById(subjectId);
                subjects.add(subject);
            }
            request.setAttribute("subjects", subjects);
            request.setAttribute("studentProgresses", studentProgresses);
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

        if (averageBall < 5) {
            return 5-averageBall;
        } else if (averageBall < 6) {
            return 6 - averageBall;
        } else if (averageBall < 8) {
            return 8-averageBall;
        } else  if (averageBall < 9) {
            return 9-averageBall;
        }
        return 0;
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
            students = studentDao.getAllWhere("WHERE Группа_НомерГруппы = ?", groupId);
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
        if (!newPhoneNumber.equals(student.getPhoneNumber()) && !newPhoneNumber.isEmpty()) {
            accountDAO.update(accountId, newPhoneNumber, "Телефон");
        }
        if (!newEmail.equals(student.getEmail()) && !newEmail.isEmpty()) {
            accountDAO.update(accountId, newEmail, "Почта");
        }
        if (!newPassword.equals(student.getPassword()) && !newPassword.isEmpty()) {
            accountDAO.update(accountId, newPassword, "Пароль");
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

}
