package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Course {
    private int id;
    private String name;
    private User instructor;
    private CourseStats courseStats;
}
