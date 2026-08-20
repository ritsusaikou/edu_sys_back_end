package com.example.demo.service;

import com.example.demo.entity.vo.CourseChoiceVO;

import java.util.List;

/**
 * @author Ritsu
 * @description 针对表【course_choice】的数据库操作Service
 * @createDate 2026-07-23 09:19:32
 */
public interface CourseChoiceService {

    List<CourseChoiceVO> getCourseChoiceVOs(Long studentId, Integer currentPage, Integer pageSize);

    Long getTotalCount();

    void chooseCourse(Long studentId, Long courseId);

    void dropCourse(Long courseChoiceId);

    List<CourseChoiceVO> getChosenCourseList(Long studentId);

    List<CourseChoiceVO> getCourseChoiceVOs(Long studentId, Integer currentPage, Integer pageSize, String courseName);

    Long getTotalCountByName(String courseName);
}
