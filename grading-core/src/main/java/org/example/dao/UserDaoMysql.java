package org.example.dao;

import org.example.dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoMysql implements UserDao {
    @Override
    public User get(int id) {
        User user = null;
        try(Connection con = DataSource.getConnection()) {
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String passwordHash = rs.getString("password_hash");
                String role = rs.getString("role");
                user = User.builder()
                        .id(id)
                        .name(name)
                        .email(email)
                        .role(role)
                        .passwordHash(passwordHash)
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try(Connection con = DataSource.getConnection()) {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String passwordHash = rs.getString("password_hash");
                String role = rs.getString("role");
                User user = User.builder()
                        .id(id)
                        .name(name)
                        .email(email)
                        .role(role)
                        .passwordHash(passwordHash)
                        .build();
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public int insert(User user) {
        int result;
        try(Connection con = DataSource.getConnection()) {
            String sql = "INSERT INTO users (name, email, password_hash, role) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getRole());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int update(User user) {
        int result;
        try(Connection con = DataSource.getConnection()) {
            String sql = "UPDATE users SET name = ?, email = ?, password_hash = ?, role = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getRole());
            ps.setInt(5, user.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int delete(User user) {
        int result;
        try(Connection con = DataSource.getConnection()) {
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public User getByEmail(String email) {
        User user = null;
        try(Connection con = DataSource.getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String passwordHash = rs.getString("password_hash");
                String role = rs.getString("role");
                user = User.builder()
                        .id(id)
                        .name(name)
                        .email(email)
                        .role(role)
                        .passwordHash(passwordHash)
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<User> getByRole(String role) {
        List<User> users = new ArrayList<>();
        try(Connection con = DataSource.getConnection()) {
            String sql = "SELECT * FROM users WHERE role = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String passwordHash = rs.getString("password_hash");
                String email = rs.getString("email");
                User user = User.builder()
                        .id(id)
                        .name(name)
                        .email(email)
                        .role(role)
                        .passwordHash(passwordHash)
                        .build();
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
