package com.example.demo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.example.demo.entity.dto.PasswordDTO;
import com.example.demo.entity.dto.UserDTO;
import com.example.demo.entity.dto.UserLoginDTO;
import com.example.demo.entity.dto.UserUpdateDTO;
import com.example.demo.entity.po.User;
import com.example.demo.entity.vo.Result;
import com.example.demo.entity.vo.TeacherVO;
import com.example.demo.entity.vo.UserVO;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.UserService;
import com.example.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

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

    @Override
    public void updatePassword(PasswordDTO passwordDTO) {
        if (passwordDTO == null) {
            throw new BusinessException("传入参数为空");
        }

        String oldPwd = passwordDTO.getOldPwd();
        if (oldPwd == null || !StringUtils.hasText(oldPwd)) {
            throw new BusinessException("旧密码不能为空");
        }

        String newPwd = passwordDTO.getNewPwd();
        if (newPwd == null || !StringUtils.hasText(newPwd)) {
            throw new BusinessException("新密码不能为空");
        }
        if (Objects.equals(newPwd, oldPwd)) {
            throw new BusinessException("新密码不能和旧密码一样");
        }

        String confirmPwd = passwordDTO.getConfirmPwd();
        if (!Objects.equals(newPwd, confirmPwd)) {
            throw new BusinessException("两次输入密码不一致");
        }

        Long userId = StpUtil.getLoginIdAsLong();
        if (userId == null || userId <= 0) {
            throw new BusinessException("会话id参数非法");
        }
        String oldPwdStored = userMapper.getPasswordById(userId);
        String oldPwdMd5 = DigestUtils.md5DigestAsHex(oldPwd.getBytes());
        if (Objects.equals(oldPwdStored, oldPwdMd5)) {
            String newPwdMd5 = DigestUtils.md5DigestAsHex(newPwd.getBytes());
            userMapper.updatePassword(userId, newPwdMd5);
        } else {
            throw new BusinessException("密码校验失败");
        }


    }

    // CRUD
    @Override
    public void create(UserDTO userDTO) {
        userMapper.create(userDTO);
    }

    @Override
    public void delete(UserDTO userDTO) {
        if (userDTO.getId() == null && userDTO.getUserNo() == null) {
            throw new BusinessException("删除条件不能为空，不能全表删除");
        }
        userMapper.delete(userDTO);
    }

    @Override
    public void update(UserDTO userDTO) {
        userMapper.update(userDTO);
    }

    @Override
    public User select(UserDTO userDTO) {
        return userMapper.select(userDTO);
    }

    @Override
    public void register(UserDTO userDTO) {
        boolean hasPhone = StringUtils.hasText(userDTO.getPhone());
        boolean hasEmail = StringUtils.hasText(userDTO.getEmail());

        if (hasPhone && hasEmail) {
            throw new BusinessException("不能同时传入手机号和邮箱");
        }
        if (!hasPhone && !hasEmail) {
            throw new BusinessException("手机号或邮箱至少填一项");
        }

        String password = userDTO.getPassword();
        if (!StringUtils.hasText(password)) {
            throw new BusinessException("必须填入密码");
        }

        User existUser = userMapper.select(userDTO);
        if (existUser != null) {
            if (hasPhone) {
                throw new BusinessException("该手机号已注册");
            } else {
                throw new BusinessException("该邮箱已注册");
            }
        }
        userDTO.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userMapper.create(userDTO);
    }

    @Override
    public UserVO login(String account, String password) {
        UserDTO queryDTO = new UserDTO();

        if (isEmail(account)) {
            queryDTO.setEmail(account);
        } else if (isPhone(account)) {
            queryDTO.setPhone(account);
        } else {
            queryDTO.setUserNo(account);
        }

        User userPO = this.select(queryDTO);

        if (userPO == null) {
            throw new BusinessException("账号不存在");
        }
        //JDK8 安全指定UTF‑8
        byte[] pwdBytes = password.getBytes(StandardCharsets.UTF_8);
        String inputMd5 = DigestUtils.md5DigestAsHex(pwdBytes);

        if (!Objects.equals(inputMd5, userPO.getPassword())) {
            throw new BusinessException("密码错误");
        }

        StpUtil.login(userPO.getId());
        return convertPoToVo(userPO);
    }

    @Override
    public UserVO info() {
        Long id = StpUtil.getLoginIdAsLong();
        if (id == null) {
            throw new BusinessException("当前会话id为空");
        } else if (id <= 0L) {
            throw new BusinessException("当前会话id非法");
        } else {
            UserDTO queryDTO = new UserDTO();
            queryDTO.setId(id);
            User selected = select(queryDTO);
            if(selected != null){
                return convertPoToVo(selected);
            }else {
                throw new BusinessException("用户信息不存在");
            }
        }
    }


    // 工具方法
    private boolean isEmail(String str) {
        //jdk8完全支持这个正则
        String reg = "^[\\w.-]+@[\\w-]+\\.[\\w]{2,}$";
        return str.matches(reg);
    }

    private boolean isPhone(String str) {
        //中国大陆手机号正则
        String reg = "^1[3-9]\\d{9}$";
        return str.matches(reg);
    }

    private UserVO convertPoToVo(User po) {
        UserVO vo = new UserVO();
        vo.setId(po.getId());
        vo.setRole(po.getRole());
        vo.setUserNo(po.getUserNo());
        vo.setName(po.getName());
        vo.setAge(po.getAge());
        vo.setPhone(po.getPhone());
        vo.setEmail(po.getEmail());
        vo.setBirthdate(po.getBirthdate());
        vo.setPhoto(po.getPhoto());
        return vo;
    }

}




