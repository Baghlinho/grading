package org.example.gradingspring.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping({"/", "/login"})
    public String viewLoginForm(){
        return "login";
    }

    @GetMapping(value="/login/success")
    public String proceedFromLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            String role = String.valueOf(auth.getAuthorities().toArray()[0]);
            switch (role) {
                case "ROLE_STUDENT":
                    return "redirect:/student";
                case "ROLE_INSTRUCTOR":
                    return "redirect:/instructor";
                case "ROLE_ADMIN":
                    return "redirect:/admin";
                default:
                    return "redirect:/logout";
            }
        }
        return "redirect:/logout";
    }
}
