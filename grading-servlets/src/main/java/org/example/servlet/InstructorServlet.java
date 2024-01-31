package org.example.servlet;

import org.example.bean.StatelessContainer;
import org.example.dto.Course;
import org.example.dto.Grade;
import org.example.dto.User;
import org.example.service.GradeBookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "instructor", urlPatterns = "/instructor")
public class InstructorServlet extends HttpServlet {

    private final GradeBookService gradeBookService = StatelessContainer.getGradeBookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (!user.getRole().equals("instructor")){
            resp.sendRedirect(req.getContextPath() + "/instructor");
            return;
        }
        String courseIdParam = req.getParameter("id");
        if(courseIdParam != null) {
            int courseId = Integer.parseInt(courseIdParam);
            List<Grade> courseGrades = gradeBookService.getCourseGrades(courseId);
            req.setAttribute("courseGrades", courseGrades);
            req.setAttribute("courseId", courseId);
            req.getRequestDispatcher("/jsp/course-grades.jsp").forward(req, resp);
            return;
        }
        List<Course> coursesWithStats = gradeBookService.getInstructorCoursesStats(user.getId());
        req.setAttribute("coursesWithStats", coursesWithStats);
        req.getRequestDispatcher("/jsp/courses-stats.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (!user.getRole().equals("instructor")){
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int gradeId = Integer.parseInt(String.valueOf(req.getParameter("gradeId")));
        int newGrade = Integer.parseInt(String.valueOf(req.getParameter("updatedGrade")));
        Grade grade = Grade
                .builder()
                .id(Integer.parseInt(String.valueOf(gradeId)))
                .gradePercent(newGrade)
                .build();
        int status = gradeBookService.updateStudentGrade(grade);
        String courseId = String.valueOf(req.getParameter("courseId"));
        String courseName = String.valueOf(req.getParameter("courseName"));
        resp.sendRedirect(req.getContextPath() + "/instructor?id=" + courseId + "&name=" + courseName);
    }
}
