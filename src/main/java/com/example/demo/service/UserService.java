package com.example.demo.service;

import com.example.demo.entity.dto.PasswordDTO;
import com.example.demo.entity.dto.UserDTO;
import com.example.demo.entity.po.User;
import com.example.demo.entity.vo.TeacherVO;
import com.example.demo.entity.vo.UserVO;

import java.util.List;

/**
 * @author Ritsu
 * @description 针对表【user】的数据库操作Service
 * @createDate 2026-07-23 09:19:31
 */
public interface UserService {

    void registerByPhone(UserDTO userDTO);

    Long getMaxId();

    void deleteById(Long id);

    User getUserById(Long id) throws Exception;

    String getPasswordById(Long id) throws Exception;

    String getPasswordByEmail(String email) throws Exception;

    String getPasswordByPhone(String phone) throws Exception;

    Long getIdByEmail(String email) throws Exception;

    Long getIdByPhone(String account) throws Exception;

    User getuserByPhone(String phone);

    List<TeacherVO> getTeacherList();

    void updatePassword(PasswordDTO passwordDTO);


    void create(UserDTO userDTO);

    void delete(UserDTO userDTO);

    void update(UserDTO userDTO);

    User select(UserDTO userDTO);

    void register(UserDTO userDTO);

    UserVO login(String account, String password);

    UserVO info();
}
