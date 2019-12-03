package servlets.filter;

import dao.AccountDAO;
import dao.UserDAO;
import model.Role;
import states.AccountantState;
import states.State;
import states.StudentState;
import states.UnknownState;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

@WebFilter(urlPatterns = "/MyServlet")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse resp = (HttpServletResponse) servletResponse;

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        final HttpSession session = req.getSession();

        AccountDAO accountDAO = new AccountDAO();

        if(nonNull(session) && nonNull(session.getAttribute("login")) && nonNull(session.getAttribute("password"))) {
            Role role = (Role) req.getSession().getAttribute("role");
            defineTheState(req, resp, filterChain, role);
        }
        else if (nonNull(login) && nonNull(password) && accountDAO.isUserExist(login, password)){
            final Role role = accountDAO.getRoleByLoginAndPassword(login, password);
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("password", password);
            req.getSession().setAttribute("role", role);
            defineTheState(req, resp, filterChain, role);
        } else {
            defineTheState(req, resp, filterChain, Role.UNKNOWN);
        }
    }

    private void defineTheState(HttpServletRequest request, HttpServletResponse response,
                                FilterChain filterChain, Role role) {
        State state = null;
        switch (role) {
            case STUDENT:
                state = new StudentState();
                break;
            case ACCOUNTANT:
                state = new AccountantState();
                break;
            case UNKNOWN:
                state = new UnknownState();
                break;
            default:
                break;
        }
        request.getSession().setAttribute("state", state);
        try {
            filterChain.doFilter(request, response);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void destroy() {
    }
}
