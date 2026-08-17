package com.example.demo.service.impl;

import com.example.demo.entity.dto.ClassDTO;
import com.example.demo.entity.po.Class;
import com.example.demo.entity.vo.ClassVO;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.ClassService;
import com.example.demo.mapper.ClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Ritsu
 * @description 针对表【class】的数据库操作Service实现
 * @createDate 2026-07-23 09:19:32
 */
@Service
public class ClassServiceImpl implements ClassService {
    private final ClassMapper classMapper;
    private final UserMapper userMapper;

    @Autowired
    public ClassServiceImpl(ClassMapper classMapper, UserMapper userMapper) {
        this.classMapper = classMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Long getMaxId() {
        return classMapper.getMaxId();
    }

    @Override
    public void add(ClassDTO classDTO) {
        classMapper.add(classDTO);
    }

    @Override
    public void deleteById(Long id) {
        classMapper.deleteById(id);
    }

    @Override
    public void update(ClassDTO classDTO) {
        classMapper.update(classDTO);
    }

    @Override
    public ClassVO getInfo(Long id) throws Exception {
        ClassVO classVO = classMapper.getInfo(id);
        if (classVO == null) {
            throw new BusinessException("班级记录为空");
        }
        return classVO;
    }

    @Override
    public List<ClassVO> getPage(Integer currentPage, Integer pageSize) {
        Integer offset = (currentPage - 1) * pageSize;
        List<ClassVO> classVOList = classMapper.getPage(offset, pageSize);
        return classVOList;
    }

    @Override
    public Long getCount() {
        Long count = classMapper.getCount();
        if (count == null) {
            count = 0L;
        }
        return count;
    }
}




