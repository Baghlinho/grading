package org.example.dao;

import org.example.dto.Grade;

import java.util.List;

public interface GradeDao extends Dao<Grade> {
    int updateGradePercent(Grade grade);

    List<Grade> getWithStudentByCourseId(int courseId);
}
