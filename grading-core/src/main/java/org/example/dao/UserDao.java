package org.example.dao;

import org.example.dto.User;

import java.util.List;

public interface UserDao extends Dao<User> {
    User getByEmail(String email);
    List<User> getByRole(String role);
}
