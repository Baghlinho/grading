package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CourseStats {
    private int min, max, studentsCount;
    private double average;
}
