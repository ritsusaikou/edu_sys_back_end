package com.example.demo.service.impl;

import com.example.demo.entity.dto.UserDTO;
import com.example.demo.entity.dto.UserUpdateDTO;
import com.example.demo.entity.po.User;
import com.example.demo.entity.vo.Result;
import com.example.demo.entity.vo.TeacherVO;
import com.example.demo.entity.vo.UserVO;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.UserService;
import com.example.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Ritsu
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2026-07-23 09:19:31
 */
@Service
public class UserServiceImpl implements UserService {


    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void registerByPhone(UserDTO userDTO) {
        userMapper.registerByPhone(userDTO);
    }

    @Override
    public Long getMaxId() {
        return userMapper.getMaxId();
    }

    @Override
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public void update(UserUpdateDTO userUpdateDTO) {
        userMapper.update(userUpdateDTO);
    }

    @Override
    public UserVO getInfo(Long id) throws Exception {
        UserVO userVO = userMapper.getInfo(id);
        if (userVO == null) {
            throw new BusinessException("用户没有记录");
        } else {
            return userVO;
        }
    }

    @Override
    public User getUserById(Long id) throws Exception {
        User user = userMapper.getUserById(id);
        if (user == null) {
            throw new BusinessException("用户没有记录");
        } else {
            return user;
        }
    }

    @Override
    public String getPasswordById(Long id) throws Exception {
        String password = userMapper.getPasswordById(id);
        if (password == null) {
            throw new BusinessException("没有密码记录");
        } else {
            return password;
        }
    }

    @Override
    public String getPasswordByEmail(String email) throws Exception {
        String password = userMapper.getPasswordByEmail(email);
        if (password == null) {
            throw new BusinessException("未设置密码");
        }
        return password;
    }

    @Override
    public String getPasswordByPhone(String phone) throws Exception {
        String password = userMapper.getPasswordByPhone(phone);
        if (password == null) {
            throw new BusinessException("未设置密码");
        }
        return password;
    }

    @Override
    public Long getIdByEmail(String email) throws Exception {
        Long id = userMapper.getIdByEmail(email);
        if (id == null) {
            throw new BusinessException("邮箱未查询到对应用户");
        }
        return id;
    }

    @Override
    public Long getIdByPhone(String phone) throws Exception {
        Long id = userMapper.getIdByPhone(phone);
        if (id == null) {
            throw new BusinessException("手机号未查询到对应用户");
        }
        return id;
    }

    @Override
    public User getuserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    @Override
    public List<TeacherVO> getTeacherList() {
        List<TeacherVO> teacherList = userMapper.getTeacherList();
        if (teacherList == null || teacherList.isEmpty()) {
            throw new RuntimeException("无法查询到教师列表");
        }
        return teacherList;
    }


}




