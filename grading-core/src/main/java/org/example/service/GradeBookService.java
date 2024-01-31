package org.example.service;

import org.example.dao.CourseDao;
import org.example.dao.GradeDao;
import org.example.dto.Course;
import org.example.dto.Grade;

import java.util.List;

public class GradeBookService {

    private final GradeDao gradeDao;
    private final CourseDao courseDao;

    public GradeBookService(GradeDao gradeDao, CourseDao courseDao) {
        this.gradeDao = gradeDao;
        this.courseDao = courseDao;
    }

    public List<Grade> getCourseGrades(int courseId) {
        return gradeDao.getWithStudentByCourseId(courseId);
    }

    public List<Course> getInstructorCoursesStats(int instructorId) {
        return courseDao.getWithStatsByInstructorId(instructorId);
    }

    public List<Course> getStudentCoursesStats(int studentId) {
        return courseDao.getWithStatsByStudentId(studentId);
    }

    public int updateStudentGrade(Grade grade) {
        return gradeDao.updateGradePercent(grade);
    }
}
