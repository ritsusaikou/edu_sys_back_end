package com.example.demo.entity.vo;


import lombok.Data;

import javax.management.openmbean.TabularData;

@Data
public class Result<T> {
    private Integer code;   // 状态码：200成功、401未登录、500异常
    private String msg;     // 给前端/用户看的提示文案
    private T data;         // 业务返回的数据，没有数据就为null

    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //只返回提示消息，data为null
    public static <T> Result<T> successMsg(String msg) {
        return new Result<>(200, msg, null);
    }

    //返回业务数据，msg默认success
    public static <T> Result<T> successData(T data) {
        return new Result<>(200, "success", data);
    }

    //完整重载（消息+数据）
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }


    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }
}
