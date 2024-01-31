package org.example.dao;

import org.example.dto.Course;
import org.example.dto.CourseStats;
import org.example.dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoMysql implements CourseDao {
    public Course get(int id) {
        Course course = null;
        try(Connection con = DataSource.getConnection()) {
            String sql = "SELECT * FROM courses WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String name = rs.getString("name");
                int instructorId = rs.getInt("instructor_id");
                User instructor = User.builder().id(instructorId).build();
                course = Course.builder().id(id).name(name).instructor(instructor).build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return course;
    }

    @Override
    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        try(Connection con = DataSource.getConnection()) {
            String sql = "SELECT * FROM courses";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int instructorId = rs.getInt("instructor_id");
                User instructor = User.builder().id(instructorId).build();
                Course course = Course.builder().id(id).name(name).instructor(instructor).build();
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    @Override
    public int insert(Course course) {
        int result;
        try(Connection con = DataSource.getConnection()) {
            String sql = "INSERT INTO courses (name, instructor_id) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, course.getName());
            ps.setInt(2, course.getInstructor().getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int update(Course course) {
        int result;
        try(Connection con = DataSource.getConnection()) {
            String sql = "UPDATE courses SET name = ?, instructor_id = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, course.getName());
            ps.setInt(2, course.getInstructor().getId());
            ps.setInt(3, course.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int delete(Course course) {
        int result;
        try(Connection con = DataSource.getConnection()) {
            String sql = "DELETE FROM courses WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, course.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Course> getWithStatsByStudentId(int studentId) {
        List<Course> courses = new ArrayList<>();
        try(Connection con = DataSource.getConnection()) {
            String sql = "SELECT c.id, c.name, AVG(grade_percent) as average, MIN(grade_percent) as minimum, MAX(grade_percent) as maximum, COUNT(student_id) as students_count\n" +
                    "FROM courses c\n" +
                    "INNER JOIN grades g\n" +
                    "ON c.id = g.course_id\n" +
                    "GROUP BY c.name\n" +
                    "HAVING MAX(CASE WHEN g.student_id = ? THEN 1 ELSE 0 END) = 1;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double average =rs.getDouble("average");
                int minimum = rs.getInt("minimum");
                int maximum = rs.getInt("maximum");
                int count = rs.getInt("students_count");
                CourseStats stats = CourseStats.builder()
                        .average(average)
                        .min(minimum)
                        .max(maximum)
                        .studentsCount(count)
                        .build();
                Course course = Course.builder()
                        .courseStats(stats)
                        .id(id)
                        .name(name)
                        .build();
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    @Override
    public List<Course> getWithStatsByInstructorId(int instructorId) {
        List<Course> courses = new ArrayList<>();
        try(Connection con = DataSource.getConnection()) {
            String sql = "SELECT c.id, c.name, AVG(grade_percent) as average, MIN(grade_percent) as minimum, MAX(grade_percent) as maximum, COUNT(student_id) as students_count\n" +
                    "FROM courses c\n" +
                    "INNER JOIN grades g\n" +
                    "ON c.id = g.course_id\n" +
                    "GROUP BY c.name\n" +
                    "HAVING MAX(CASE WHEN c.instructor_id = ? THEN 1 ELSE 0 END) = 1;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, instructorId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double average =rs.getDouble("average");
                int minimum = rs.getInt("minimum");
                int maximum = rs.getInt("maximum");
                int count = rs.getInt("students_count");
                CourseStats stats = CourseStats.builder()
                        .average(average)
                        .min(minimum)
                        .max(maximum)
                        .studentsCount(count)
                        .build();
                Course course = Course.builder()
                        .courseStats(stats)
                        .id(id)
                        .name(name)
                        .build();
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }
}
