package org.example.dao;

import org.example.dto.Course;
import org.example.dto.Grade;
import org.example.dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDaoMysql implements GradeDao {
    public Grade get(int id) {
        Grade grade = null;
        try(Connection con = DataSource.getConnection()) {
            String sql = "SELECT * FROM grades WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int courseId = rs.getInt("course_id");
                int studentId = rs.getInt("student_id");
                int gradePercent = rs.getInt("grade_percent");
                Course course = Course.builder()
                        .id(courseId)
                        .build();
                User student = User.builder()
                        .id(studentId)
                        .build();
                grade = Grade.builder()
                        .id(id)
                        .course(course)
                        .student(student)
                        .gradePercent(gradePercent)
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return grade;
    }

    @Override
    public List<Grade> getAll() {
        List<Grade> grades = new ArrayList<>();
        try(Connection con = DataSource.getConnection()) {
            String sql = "SELECT * FROM grades";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                int courseId = rs.getInt("course_id");
                int studentId = rs.getInt("student_id");
                int gradePercent = rs.getInt("grade_percent");
                Course course = Course.builder()
                        .id(courseId)
                        .build();
                User student = User.builder()
                        .id(studentId)
                        .build();
                Grade grade = Grade.builder()
                        .id(id)
                        .course(course)
                        .student(student)
                        .gradePercent(gradePercent)
                        .build();
                grades.add(grade);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return grades;
    }

    @Override
    public int insert(Grade grade) {
        int result;
        try(Connection con = DataSource.getConnection()) {
            String sql = "INSERT INTO grades (course_id, student_id, grade_percent) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, grade.getCourse().getId());
            ps.setInt(2, grade.getStudent().getId());
            ps.setInt(3, grade.getGradePercent());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int update(Grade grade) {
        int result;
        try(Connection con = DataSource.getConnection()) {
            String sql = "UPDATE grades SET course_id = ?, student_id = ?, grade_percent = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, grade.getCourse().getId());
            ps.setInt(2, grade.getStudent().getId());
            ps.setInt(3, grade.getGradePercent());
            ps.setInt(4, grade.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int updateGradePercent(Grade grade) {
        int result;
        try(Connection con = DataSource.getConnection()) {
            String sql = "UPDATE grades SET grade_percent = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, grade.getGradePercent());
            ps.setInt(2, grade.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int delete(Grade grade) {
        int result;
        try(Connection con = DataSource.getConnection()) {
            String sql = "DELETE FROM grades WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, grade.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Grade> getWithStudentByCourseId(int courseId) {
        List<Grade> grades = new ArrayList<>();
        try(Connection con = DataSource.getConnection()) {
            String sql = "SELECT g.id, g.student_id, s.name, g.grade_percent " +
                    "FROM users s " +
                    "INNER JOIN grades g " +
                    "ON s.id = g.student_id " +
                    "WHERE g.course_id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                int studentId = rs.getInt("student_id");
                String name = rs.getString("name");
                int gradePercent = rs.getInt("grade_percent");
                User student = User.builder()
                        .id(studentId)
                        .name(name)
                        .build();
                Grade grade = Grade.builder()
                        .id(id)
                        .student(student)
                        .gradePercent(gradePercent)
                        .build();
                grades.add(grade);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return grades;
    }
}
