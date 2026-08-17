package com.example.demo.mapper;

import com.example.demo.entity.vo.CourseChoiceVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Ritsu
 * @description 针对表【course_choice】的数据库操作Mapper
 * @createDate 2026-07-23 09:19:32
 * @Entity com.example.demo.entity.po.CourseChoice
 */
public interface CourseChoiceMapper {

    List<CourseChoiceVO> getCourseChoiceVOList(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    List<Long> getChosenCourseIdList(@Param("studentId") Long studentId);

    Long getTotalCount();

    void chooseCourse(@Param("id") Long id, @Param("studentId") Long studentId, @Param("courseId") Long courseId);

    Long getMaxId();

    void dropCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    List<CourseChoiceVO> getChosenCourseList(@Param("studentId") Long studentId);
}




