package com.example.demo.service;

import com.example.demo.entity.dto.CourseDTO;
import com.example.demo.entity.po.Course;
import com.example.demo.entity.vo.CourseVO;
import com.example.demo.entity.vo.TaughtCourseVO;

import java.util.List;

/**
 * @author Ritsu
 * @description 针对表【course】的数据库操作Service
 * @createDate 2026-07-23 09:19:32
 */
public interface CourseService {

    Long getMaxId();

    void add(CourseDTO courseDTO);

    void deleteById(Long id);

    void update(CourseDTO courseDTO);

    CourseVO getInfo(Long id) throws Exception;

    List<CourseVO> getPage(Integer currentPage, Integer pageSize);

    Long getCount();

    List<TaughtCourseVO> getTaughtCourseList(Long userId);
}
