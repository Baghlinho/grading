package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Grade {
    private int id;
    private Course course;
    private User student;
    private int gradePercent;
}
