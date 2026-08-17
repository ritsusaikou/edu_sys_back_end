package com.example.demo.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.example.demo.entity.dto.*;
import com.example.demo.entity.po.User;
import com.example.demo.entity.vo.Result;
import com.example.demo.entity.vo.TeacherVO;
import com.example.demo.entity.vo.UserVO;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.UserService;

import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/registerByPhone")
    public Result regiesterByPhone(@RequestBody UserDTO userDTO) throws Exception {
        if (userDTO == null) {
            throw new BusinessException("参数不能为空");
        }
        User user = userService.getuserByPhone(userDTO.getPhone());
        if (user != null) {
            throw new BusinessException("该手机号已注册");
        }
        String password = userDTO.getPassword();
        if (!StringUtils.hasText(password)) { // 如果密码为空（密码）
            throw new BusinessException("密码传入不能为空");
        } else {
            userDTO.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        }
        Long id = userService.getMaxId();
        if (id == null) {
            id = 1L;
        } else {
            id += 1;
        }
        userDTO.setId(id);
        userService.registerByPhone(userDTO);
        return Result.successMsg(" 注册成功");
    }


    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam Long id) {
        userService.deleteById(id);
        return Result.successMsg("删除成功");
    }

    @PutMapping("/update")
    public Result update(@RequestBody UserUpdateDTO userUpdateDTO) throws Exception {
        if (userUpdateDTO == null) {
            throw new BusinessException("传入参数为空");
        } else {
            userService.update(userUpdateDTO);
            return Result.successMsg("修改成功");
        }
    }

    @GetMapping("/getInfo")
    public Result getInfo() throws Exception {
        Long id = StpUtil.getLoginIdAsLong();
        if (id == null) {
            throw new BusinessException("当前会话id为空");
        } else if (id == 0L) {
            throw new BusinessException("当前会话id不能等于0");
        } else {
            UserVO userVO = userService.getInfo(id);
            return Result.success("成功获取用户信息", userVO);
        }
    }


    @PostMapping("/loginById")
    public Result loginById(@RequestBody UserLoginDTO userLoginDTO) throws Exception {
        if (userLoginDTO == null) {
            throw new BusinessException("传入参数为空");
        }
        Long id = userLoginDTO.getId();
        if (id == null) {
            throw new BusinessException("id不能为空");
        }
        if (id == 0L) {
            throw new BusinessException("id不能为0");
        }
        String pwd = userLoginDTO.getPassword();
        if (!StringUtils.hasText(pwd)) {
            throw new BusinessException("传入密码没有内容");
        }
        String md5Pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        String dbPwd = userService.getPasswordById(id);
        if (md5Pwd.equals(dbPwd)) {
            StpUtil.login(userLoginDTO.getId());
            return Result.successMsg("登录成功");
        }
        return Result.error("密码不正确");
    }

    @PostMapping("/loginByAccount")   // 通过手机号或用户号登录
    public Result loginByAccount(@RequestBody UserLoginByAccountDTO userLoginByAccountDTO) throws Exception {
        if (userLoginByAccountDTO == null
                || userLoginByAccountDTO.getAccount() == null
                || userLoginByAccountDTO.getPassword() == null) {
            throw new BusinessException("传入参数为空");
        }

        String account = userLoginByAccountDTO.getAccount();
        String dbPwd;
        Long id;
        if (userLoginByAccountDTO.getAccount().contains("@")) {
            id = userService.getIdByEmail(account);
            dbPwd = userService.getPasswordByEmail(account);
        } else {
            id = userService.getIdByPhone(account);
            dbPwd = userService.getPasswordByPhone(account);
        }

        String pwd = userLoginByAccountDTO.getPassword();
        String md5Pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        if (md5Pwd.equals(dbPwd)) {
            StpUtil.login(id);
            return Result.successMsg("登录成功");
        }
        return Result.error("密码错误");
    }

    @PostMapping("/logout")
    public Result logout() {
        StpUtil.logout();
        return Result.successMsg("当前会话已注销");
    }

    @GetMapping("/getTeacherList")
    public Result getTeacherList() {
        return Result.success("成功查询到教师列表", userService.getTeacherList());
    }


}
