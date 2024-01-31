package org.example.gradingspring.controllers;

import org.example.dto.Course;
import org.example.dto.Grade;
import org.example.dto.User;
import org.example.service.GradeBookService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/instructor")
public class InstructorController {

    private final GradeBookService gradeBookService;
    private final UserService userService;

    @Autowired
    public InstructorController(GradeBookService gradeBookService, UserService userService) {
        this.gradeBookService = gradeBookService;
        this.userService = userService;
    }
    @GetMapping
    public ModelAndView instructorDashboard() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ModelAndView mv = new ModelAndView();
        User instructor = userService.getUserByEmail(email);
        List<Course> coursesWithStats = gradeBookService.getInstructorCoursesStats(instructor.getId());
        mv.addObject("user", instructor);
        mv.addObject("email", email);
        mv.addObject("coursesWithStats", coursesWithStats);
        mv.setViewName("courses-stats");
        return mv;
    }

    @GetMapping("/course-grades")
    public ModelAndView courseGrades(@RequestParam("id") int courseId, @RequestParam("name") String courseName) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User instructor = userService.getUserByEmail(email);
        ModelAndView mv = new ModelAndView("course-grades");
        mv.addObject("user", instructor);
        List<Grade> courseGrades = gradeBookService.getCourseGrades(courseId);
        mv.addObject("courseName", courseName);
        mv.addObject("courseGrades", courseGrades);
        return mv;
    }

    @PostMapping("/update-grade")
    public String updateGrade(@RequestParam("gradeId") int gradeId,
                              @RequestParam("courseId") int courseId,
                              @RequestParam("courseName") String courseName,
                              @RequestParam("newGrade") int newGrade) {

        Grade grade = Grade
                .builder()
                .id(Integer.parseInt(String.valueOf(gradeId)))
                .gradePercent(newGrade)
                .build();
        gradeBookService.updateStudentGrade(grade);
        return "redirect:/instructor/course-grades?id=" + courseId + "&name=" + courseName;
    }

}
