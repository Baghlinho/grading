package org.example.gradingspring.controllers;


import org.example.dto.Course;
import org.example.dto.User;
import org.example.service.PasswordService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PasswordService passwordService;

    @Autowired
    public AdminController(UserService userService, PasswordService passwordService) {
        this.userService = userService;
        this.passwordService = passwordService;
    }

    @GetMapping
    public ModelAndView adminDashboard() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ModelAndView mv = new ModelAndView();
        User admin = userService.getUserByEmail(email);
        mv.addObject("user", admin);
        mv.setViewName("user-management");
        return mv;
    }

    @GetMapping("/list")
    public ModelAndView showUsersByRole(@RequestParam("role") String role) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ModelAndView mv = new ModelAndView();
        User admin = userService.getUserByEmail(email);
        List<? extends User> userList = userService.getUsersByRole(role);
        mv.addObject("usersRole", role);
        mv.addObject("usersList", userList);
        mv.addObject("user", admin);
        mv.setViewName("users-list");
        return mv;
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("actionRole") String role,
                             @RequestParam("userId") int id) {

        User user = User.builder().id(id).build();
        userService.deleteUser(user);
        return "redirect:/admin/list?role=" + role;
    }

    @PostMapping("/add")
    public String addUser(@RequestParam("email") String email,
                          @RequestParam("name") String name,
                          @RequestParam("actionRole") String role) {

        String passwordHash = passwordService.hashPassword("123");
        User user = User.builder().name(name).email(email).passwordHash(passwordHash).role(role).build();
        userService.addUser(user);
        return "redirect:/admin/list?role=" + role;
    }
}
