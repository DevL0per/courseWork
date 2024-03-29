package servlets.servlet;

import states.State;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        State state = (State) req.getSession().getAttribute("state");
        String parameter = (String) req.getParameter("parameter");
        if (parameter == null) {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }
        switch (parameter) {
            case "регистрация":
                state.doRegistration(req, resp);
                break;
            case "вход":
                state.doLogin(req, resp);
                break;
            case "факультеты":
                state.showAllFaculties(req, resp);
                break;
            case "показать группы специальности":
                Integer specialtyId = Integer.valueOf(req.getParameter("specialtyId"));
                req.removeAttribute("specialtyId");
                state.showAllGroupsInSpecialty(req, resp, specialtyId);
                break;
            case "показать специальности факультета":
                Integer facultyId = Integer.valueOf(req.getParameter("facultyId"));
                state.showAllSpecialtyInFaculty(req, resp, facultyId);
                break;
            case "кабинет":
                state.gotoCabinet(req, resp);
                break;
            case "выйти":
                state.doLogout(req, resp);
                break;
            case "список студентов":
                state.showAllStudentsInGroup(req, resp);
                break;
            case "заблокировать студента":
            case "разблокировать студента":
                state.banAccount(req, resp);
                break;
            case "выставить оценоки":
                state.showStudentGradesScreen(req, resp);
                break;
            case "профиль студента":
                state.goToStudentProfile(req, resp);
                break;
            case "добавить предмет":
                state.addSubject(req, resp);
                break;
            case "редактировать оценки":
                state.editStudentGrades(req, resp);
                break;
            case "редактировать аккаунт":
                state.editAccount(req, resp);
                break;
            case "добавить факультет":
                state.addFaculty(req, resp);
                break;
            case "добавить специальность":
                state.addSpecialty(req, resp);
                break;
            case "добавить группу":
                state.addGroup(req, resp);
                break;
            case "рассчитать":
                state.addStudentGrades(req, resp);
                break;
            case "показать статистику":
                state.showStatistic(req, resp);
                break;
            case "расписание":
                state.showSchedule(req, resp);
                break;
            default:
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF8");
        State state = (State) req.getSession().getAttribute("state");
        String parameter = (String) req.getParameter("parameter");

        if (parameter == null) {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
        assert parameter != null;
        switch (parameter) {
            case "добавить факультет":
                state.addFaculty(req, resp);
                break;
            case "добавить специальность":
                state.addSpecialty(req, resp);
                break;
            case "добавить группу":
                state.addGroup(req, resp);
                break;
            case "добавить предмет":
                state.addSubject(req, resp);
                break;
            case "редактировать аккаунт":
                state.editAccount(req, resp);
                break;
            case "редактировать оценки":
                state.editStudentGrades(req, resp);
                break;
        }
    }
}
