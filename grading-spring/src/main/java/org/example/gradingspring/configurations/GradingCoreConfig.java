package org.example.gradingspring.configurations;

import org.example.dao.*;
import org.example.service.GradeBookService;
import org.example.service.PasswordService;
import org.example.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GradingCoreConfig {
    @Bean
    public UserDao userDao() {
        return new UserDaoMysql();
    }

    @Bean
    public CourseDao courseDao() {
        return new CourseDaoMysql();
    }

    @Bean
    public GradeDao gradeDao() {
        return new GradeDaoMysql();
    }

    @Bean
    public GradeBookService gradeBookService() {
        return new GradeBookService(gradeDao(), courseDao());
    }

    @Bean
    public PasswordService passwordSecurity() {
        return new PasswordService();
    }

    @Bean
    public UserService userService() {
        return new UserService(userDao());
    }
}
