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
                Integer groupId = Integer.valueOf(req.getParameter("group"));
                state.showAllStudentsInGroup(req, resp, groupId);
                break;
            case "ввод оценок":
                state.showStudentGradesScreen(req, resp);
                break;
            case "рассчитать":
                state.calculateScholarship(req, resp);
                break;
            default:
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


}
