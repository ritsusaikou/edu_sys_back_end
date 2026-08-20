package com.example.demo.mapper;

import com.example.demo.entity.dto.CourseDTO;
import com.example.demo.entity.po.Course;
import com.example.demo.entity.vo.CourseVO;
import com.example.demo.entity.vo.TaughtCourseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Ritsu
 * @description 针对表【course】的数据库操作Mapper
 * @createDate 2026-07-23 09:19:32
 * @Entity com.example.demo.entity.po.Course
 */
public interface CourseMapper {

    Long getMaxId();

    void add(CourseDTO courseDTO);

    void deleteById(Long id);

    void update(CourseDTO courseDTO);

    CourseVO getInfo(Long id);

    List<CourseVO> getPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    Long getCount();

    List<TaughtCourseVO> getTaughtCourseList(@Param("userId") Long userId);
}




