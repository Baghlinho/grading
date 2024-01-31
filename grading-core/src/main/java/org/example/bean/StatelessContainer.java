package org.example.bean;

import lombok.Getter;
import org.example.dao.*;
import org.example.service.*;

public class StatelessContainer {

    @Getter
    private static final GradeDao gradeDao = new GradeDaoMysql();
    @Getter
    private static final CourseDao courseDao = new CourseDaoMysql();
    @Getter
    private static final UserDao userDao = new UserDaoMysql();
    @Getter
    private static final PasswordService passwordService = new PasswordService();
    @Getter
    private static final GradeBookService gradeBookService = new GradeBookService(getGradeDao(), getCourseDao());
    @Getter
    private static final UserService userService = new UserService(getUserDao());
}
