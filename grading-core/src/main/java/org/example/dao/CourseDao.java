package org.example.dao;

import org.example.dto.Course;

import java.util.List;

public interface CourseDao extends Dao<Course> {

    List<Course> getWithStatsByStudentId(int studentId);

    List<Course> getWithStatsByInstructorId(int instructorId);
}
