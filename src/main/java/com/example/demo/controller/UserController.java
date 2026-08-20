package com.example.demo.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.example.demo.entity.dto.*;
import com.example.demo.entity.vo.Result;
import com.example.demo.entity.vo.UserVO;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result regiester(@RequestBody UserDTO userDTO) throws Exception {
        userService.register(userDTO);
        return Result.successMsg(" 注册成功");
    }

    @GetMapping("/info")
    public Result info() throws Exception {
        UserVO userVO = userService.info();
        return Result.success("成功获取用户信息", userVO);
    }

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody PasswordDTO passwordDTO) {
        userService.updatePassword(passwordDTO);
        return Result.successMsg("修改密码成功");
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam Long id) {
        userService.deleteById(id);
        return Result.successMsg("删除成功");
    }

    @PutMapping("/update")
    public Result update(@RequestBody UserDTO userDTO) throws Exception {
        userService.update(userDTO);
        return Result.successMsg("修改成功");
    }


    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO) throws BusinessException {
        // 基础参数校验
        if (userLoginDTO == null) {
            throw new BusinessException("请求参数不能为空");
        }
        String account = userLoginDTO.getAccount();
        String password = userLoginDTO.getPassword();
        if (account == null || account.trim().isEmpty()) {
            throw new BusinessException("账号不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new BusinessException("密码不能为空");
        }
        account = account.trim();
        password = password.trim();

        UserVO userInfo = userService.login(account, password);
        return Result.success("登录成功", userInfo);
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
            id = userService.select(new UserDTO()).getId();
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
