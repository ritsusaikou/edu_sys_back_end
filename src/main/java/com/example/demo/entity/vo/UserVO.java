package com.example.demo.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVO {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 角色：1：学生，2：老师，3：管理员
     */
    private Integer role;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 出生日期
     */
    private Date birthdate;

    /**
     * 照片
     */
    private String photo;


    /**
     * 学号（学生）/教师号（教师）/管理员号（管理员）
     */
    private String userNo;

}
