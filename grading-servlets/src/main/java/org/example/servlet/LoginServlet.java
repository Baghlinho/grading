package org.example.servlet;

import org.example.bean.StatelessContainer;
import org.example.dto.User;
import org.example.service.PasswordService;
import org.example.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = StatelessContainer.getUserService();
    private final PasswordService passwordService = StatelessContainer.getPasswordService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if(user != null) resp.sendRedirect(req.getContextPath() + "/" + user.getRole());
        else req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user;
        if((user = (User) session.getAttribute("user")) != null) {
            resp.sendRedirect(req.getContextPath() + "/" + user.getRole());
            return;
        }
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        user = userService.getUserByEmail(email);
        if(user != null) {
            boolean isUserValid = passwordService.validatePassword(password, user.getPasswordHash());
            if (isUserValid) {
                session.setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/" + user.getRole());
                return;
            }
        }
        req.setAttribute("error", "Incorrect email and password");
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }
}