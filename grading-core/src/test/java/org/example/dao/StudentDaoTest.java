package org.example.dao;

import org.example.dto.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    void testUserDaoMysql() {
        UserDao userDao = new UserDaoMysql();

        User student = User.builder().name("Test").email("test@example.com").passwordHash("testpasshash").build();
        System.out.println(student);
        assertEquals(1, userDao.insert(student));

        student = userDao.get(userDao.getByEmail("test@example.com").getId());
        System.out.println(student);
        assertEquals("test@example.com", student.getEmail());

        student = User.builder().name("Updated").email("updated@example.com").passwordHash("updatedpasshash").build();
        System.out.println(student);
        assertEquals(1, userDao.update(student));

        List<User> students = userDao.getAll();
        System.out.println(students);
        assertNotNull(students);

        System.out.println(student);
        assertEquals(1, userDao.delete(student));
    }
}