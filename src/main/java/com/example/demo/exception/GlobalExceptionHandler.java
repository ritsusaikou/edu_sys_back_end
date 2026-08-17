package com.example.demo.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.example.demo.entity.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ResponseBody
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotLoginException.class)
    public Result NotLoginExceptionException(Exception e, HttpServletResponse resp) {
        resp.setStatus(401);
        log.error(e.getMessage(), e);
        return Result.error(401, "unauthenticated" + e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(Exception e) {
        e.printStackTrace();
        return Result.error("业务异常:" + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error("服务器异常:" + e.getMessage());
    }
}

