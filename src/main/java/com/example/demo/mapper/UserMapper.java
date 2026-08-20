package com.example.demo.mapper;

import com.example.demo.entity.dto.UserDTO;
import com.example.demo.entity.dto.UserUpdateDTO;
import com.example.demo.entity.po.User;
import com.example.demo.entity.vo.TeacherVO;
import com.example.demo.entity.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Ritsu
 * @description 针对表【user】的数据库操作Mapper
 * @createDate 2026-07-23 09:19:31
 * @Entity com.example.demo.entity.po.User
 */
public interface UserMapper {

    void registerByPhone(UserDTO userDTO);

    Long getMaxId();

    void deleteById(Long id);

    void update(UserUpdateDTO userUpdateDTO);

    UserVO getInfo(Long id);

    User getUserById(Long id);

    String getPasswordById(Long id);

    String getPasswordByEmail(String email);

    String getPasswordByPhone(String phone);

    Long getIdByEmail(String email);

    Long getIdByPhone(String phone);

    User getUserByPhone(String phone);

    List<TeacherVO> getTeacherList();

    void updatePassword(@Param("userId") Long userId,@Param("newPwd") String newPwd);
}




