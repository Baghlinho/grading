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

public class StudentPortal implements Portal {
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
                    int studentId = Integer.parseInt(line);
                    return getStudentCourseStatsData(studentId);
                case "view-grades":
                    line = requestReader.readLine();
                    int courseId = Integer.parseInt(line);
                    return getCourseGradesData(courseId);
                default:
                    throw new IllegalArgumentException("Invalid student action");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getStudentCourseStatsData(int studentId) {
        List<Course> coursesStats = gradeBookService.getStudentCoursesStats(studentId);
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
