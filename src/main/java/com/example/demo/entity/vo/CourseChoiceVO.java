package com.example.demo.entity.vo;

import lombok.Data;

@Data
public class CourseChoiceVO {
    private Long courseChoiceId;
    private Long courseId;
    private String courseNo;
    private String courseName;
    private Long teacherId;
    private String teacherName;
    private String teacherNo;
    private Boolean chosen = false;
}
