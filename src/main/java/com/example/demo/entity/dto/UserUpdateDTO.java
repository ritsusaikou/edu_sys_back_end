package com.example.demo.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserUpdateDTO {
    private Long id;
    private Integer role;
    private String name;
    private Integer age;
    private String phone;
    private String email;
    private Date birthdate;
    private String photo;
    private String userNo;
}
