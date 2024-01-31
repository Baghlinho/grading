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
import java.util.List;

@WebServlet(name = "admin", urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {
    private final UserService userService = StatelessContainer.getUserService();
    private final PasswordService passwordService = StatelessContainer.getPasswordService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (!user.getRole().equals("admin")){
            resp.sendRedirect(req.getContextPath() + "/admin");
             return;
        }
        String role = req.getParameter("list");
        if (role != null) {
            List<? extends User> userList = userService.getUsersByRole(role);
            req.setAttribute("usersList", userList);
            req.setAttribute("usersRole", role);
            req.getRequestDispatcher("/jsp/users-list.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/jsp/user-management.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (!user.getRole().equals("admin")){
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String adminAction = String.valueOf(req.getParameter("action"));
        String actionRole = String.valueOf(req.getParameter("actionRole"));
        if("delete".equals(adminAction)) {
            int userId = Integer.parseInt(String.valueOf(req.getParameter("userId")));
            user = User.builder().id(userId).build();
            userService.deleteUser(user);
        }
        else if("add".equals(adminAction)) {
            String email = String.valueOf(req.getParameter("email"));
            String name = String.valueOf(req.getParameter("name"));
            String passwordHash = passwordService.hashPassword("123");
            user = User.builder().name(name).email(email).passwordHash(passwordHash).role(actionRole).build();
            userService.addUser(user);
        }
        resp.sendRedirect(req.getContextPath() + "/admin?list=" + actionRole);
    }
}
