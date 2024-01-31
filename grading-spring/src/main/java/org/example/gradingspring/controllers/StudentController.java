package org.example.gradingspring.controllers;

import org.example.dto.Course;
import org.example.dto.Grade;
import org.example.dto.User;
import org.example.service.GradeBookService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final GradeBookService gradeBookService;
    private final UserService userService;

    @Autowired
    public StudentController(GradeBookService gradeBookService, UserService userService) {
        this.gradeBookService = gradeBookService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView studentDashboard() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ModelAndView mv = new ModelAndView();
        User student = userService.getUserByEmail(email);
        List<Course> coursesWithStats = gradeBookService.getStudentCoursesStats(student.getId());
        mv.addObject("user", student);
        mv.addObject("email", email);
        mv.addObject("coursesWithStats", coursesWithStats);
        mv.setViewName("courses-stats");
        return mv;
    }

    @GetMapping("/course-grades")
    public ModelAndView courseGrades(@RequestParam("id") int courseId, @RequestParam("name") String courseName) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userService.getUserByEmail(email);
        ModelAndView mv = new ModelAndView("course-grades");
        mv.addObject("user", student);
        List<Grade> courseGrades = gradeBookService.getCourseGrades(courseId);
        mv.addObject("courseName", courseName);
        mv.addObject("courseGrades", courseGrades);
        return mv;
    }
}
