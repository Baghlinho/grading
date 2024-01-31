package org.example.service;

import org.example.dao.UserDao;
import org.example.dto.User;

import java.util.List;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public int addUser(User user) {
        return userDao.insert(user);
    }

    public int deleteUser(User user) {
        return userDao.delete(user);
    }

    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    public List<User> getUsersByRole(String role) {
        return userDao.getByRole(role);
    }
}
