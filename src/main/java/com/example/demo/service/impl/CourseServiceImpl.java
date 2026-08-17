package com.example.demo.service.impl;

import com.example.demo.entity.dto.CourseDTO;
import com.example.demo.entity.po.Course;
import com.example.demo.entity.vo.CourseVO;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.CourseService;
import com.example.demo.mapper.CourseMapper;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Ritsu
 * @description 针对表【course】的数据库操作Service实现
 * @createDate 2026-07-23 09:19:32
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public Long getMaxId() {
        return courseMapper.getMaxId();
    }

    @Override
    public void add(CourseDTO courseDTO) {
        courseMapper.add(courseDTO);
    }

    @Override
    public void deleteById(Long id) {
        courseMapper.deleteById(id);
    }

    @Override
    public void update(CourseDTO courseDTO) {
        courseMapper.update(courseDTO);
    }

    @Override
    public CourseVO getInfo(Long id) throws Exception {
        CourseVO courseVO = courseMapper.getInfo(id);
        if (courseVO == null) {
            throw new BusinessException("未找到对应id信息");
        }
        return courseVO;
    }

    @Override
    public List<CourseVO> getPage(Integer currentPage, Integer pageSize) {
        Integer offset = (currentPage - 1) * pageSize;
        List<CourseVO> courseVOList = courseMapper.getPage(offset, pageSize);
        return courseVOList;
    }

    @Override
    public Long getCount() {
        Long count = courseMapper.getCount();
        if (count == null) {
            count = 0L;
        }
        return count;
    }
}




