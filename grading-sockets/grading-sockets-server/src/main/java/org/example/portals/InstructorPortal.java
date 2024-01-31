package org.example.portals;

import org.example.ServerState;
import org.example.bean.StatelessContainer;
import org.example.dto.Course;
import org.example.dto.Grade;
import org.example.service.GradeBookService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class InstructorPortal implements Portal {

    private final GradeBookService gradeBookService = StatelessContainer.getGradeBookService();
    @Override
    public String handleRequest(String request, ServerState serverState) {
        try(BufferedReader requestReader = new BufferedReader(new StringReader(request))) {
            String line = requestReader.readLine();
            switch (line) {
                case "logout":
                    serverState.setCurrent(serverState.getLogin());
                    return "logout-success\n";
                case "view-stats":
                    line = requestReader.readLine();
                    int instructorId = Integer.parseInt(line);
                    return getInstructorCourseStatsData(instructorId);
                case "view-grades":
                    line = requestReader.readLine();
                    int courseId = Integer.parseInt(line);
                    return getCourseGradesData(courseId);
                case "update-grade":
                    int gradeId = Integer.parseInt(requestReader.readLine());
                    int newGrade = Integer.parseInt(requestReader.readLine());
                    return updateStudentGradeData(gradeId, newGrade);
                default:
                    throw new IllegalArgumentException("Invalid instructor action");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String updateStudentGradeData(int gradeId, int gradePercent) {
        Grade newGrade = Grade.builder().id(gradeId).gradePercent(gradePercent).build();
        int status = gradeBookService.updateStudentGrade(newGrade);
        return status == 1 ? "update-grade-success\n" : "\n";
    }

    private String getInstructorCourseStatsData(int instructorId) {
        List<Course> coursesStats = gradeBookService.getInstructorCoursesStats(instructorId);
        String header = "ID, Course, Average, Minimum, Maximum, Students Count\n";
        StringBuilder response = new StringBuilder(header);
        coursesStats.forEach(record -> response
                .append(record.getId()).append(", ")
                .append(record.getName()).append(", ")
                .append(record.getCourseStats().getAverage()).append(", ")
                .append(record.getCourseStats().getMin()).append(", ")
                .append(record.getCourseStats().getMax()).append(", ")
                .append(record.getCourseStats().getStudentsCount()).append("\n")
        );
        return "view-stats-success\n" + response;
    }

    private String getCourseGradesData(int courseId) {
        List<Grade> courseGrades = gradeBookService.getCourseGrades(courseId);
        String header = "ID, Student ID, Name, Grade\n";
        StringBuilder response = new StringBuilder(header);
        courseGrades.forEach(record -> response
                .append(record.getId()).append(", ")
                .append(record.getStudent().getId()).append(", ")
                .append(record.getStudent().getName()).append(", ")
                .append(record.getGradePercent()).append("\n")
        );
        return "view-grades-success\n" + response;
    }
}
