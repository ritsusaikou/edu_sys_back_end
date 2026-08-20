package com.example.demo.entity.dto;

import lombok.Data;

@Data
public class PasswordDTO {
    private String oldPwd;
    private String newPwd;
    private String confirmPwd;
}
