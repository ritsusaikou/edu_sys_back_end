package com.example.demo.entity.dto;


import lombok.Data;

@Data
public class CourseDTO {
    private Long id;
    private String courseNo;
    private String courseName;
    private Integer userId;
}
