package com.example.demo.service.impl;

import com.example.demo.entity.vo.ChoiceVO;
import com.example.demo.entity.vo.CourseChoiceVO;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.CourseChoiceService;
import com.example.demo.mapper.CourseChoiceMapper;
import com.example.demo.service.ScoreService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ritsu
 * @description 针对表【course_choice】的数据库操作Service实现
 * @createDate 2026-07-23 09:19:32
 */
@Service
public class CourseChoiceServiceImpl implements CourseChoiceService {


    private final CourseChoiceMapper courseChoiceMapper;
    private final ScoreService scoreService;

    public CourseChoiceServiceImpl(CourseChoiceMapper courseChoiceMapper,ScoreServiceImpl scoreService) {
        this.courseChoiceMapper = courseChoiceMapper;
        this.scoreService = scoreService;
    }

    @Override
    public List<CourseChoiceVO> getCourseChoiceVOs(Long studentId, Integer currentPage, Integer pageSize) {
        if (studentId == null) {
            throw new BusinessException("登录用户ID不能为空");
        }
        if (currentPage == null || currentPage < 1) {
            throw new BusinessException("当前页码必须大于等于1");
        }
        if (pageSize == null || pageSize < 1 || pageSize > 40) {
            throw new BusinessException("每页条数合法范围1~40");
        }
        Integer offset = (currentPage - 1) * pageSize;
        List<CourseChoiceVO> courseChoiceVOList = courseChoiceMapper.getCourseChoiceVOs(offset, pageSize);
        Set<Long> selectedCourseIdSet = new HashSet<>(courseChoiceMapper.getChosenCourseIdList(studentId));
        courseChoiceVOList.forEach(vo -> vo.setChosen(selectedCourseIdSet.contains(vo.getCourseId())));
        return courseChoiceVOList;
    }

    @Override
    public Long getTotalCount() {
        Long totalCount = courseChoiceMapper.getTotalCount();
        if (totalCount == null || totalCount < 0) {
            return 0L;
        }
        return totalCount;
    }

    @Override
    public void chooseCourse(Long studentId, Long courseId) {
        if (studentId == null || courseId == null || studentId < 0 || courseId < 0) {
            throw new BusinessException("学生id和课程id非法");
        }
        Long id = courseChoiceMapper.getMaxId();
        if (id == null || id <= 0) {
            id = 1L;
        } else {
            id = id + 1l;
        }
        courseChoiceMapper.chooseCourse(id, studentId, courseId);
        scoreService.initEmptyScore(studentId, courseId);
    }

    @Override
    public void dropCourse(Long courseChoiceId) {
        if (courseChoiceId == null || courseChoiceId <= 0) {
            throw new BusinessException("参数非法");
        }
        ChoiceVO choice = courseChoiceMapper.getById(courseChoiceId);
        if(choice == null){
            throw new BusinessException("选课记录不存在");
        }
        scoreService.deleteScore(choice.getCourseId(),choice.getStudentId());
        courseChoiceMapper.dropCourse(courseChoiceId);

    }

    @Override
    public List<CourseChoiceVO> getChosenCourseList(Long studentId) {
        if (studentId == null || studentId <= 0) {
            throw new BusinessException("参数非法");
        }
        List<CourseChoiceVO> chosenCourseList = courseChoiceMapper.getChosenCourseList(studentId);
        return chosenCourseList;
    }

    @Override
    public List<CourseChoiceVO> getCourseChoiceVOs(Long studentId, Integer currentPage, Integer pageSize, String courseName) {
        if (studentId == null) {
            throw new BusinessException("登录用户ID不能为空");
        }
        if (currentPage == null || currentPage < 1) {
            throw new BusinessException("当前页码必须大于等于1");
        }
        if (pageSize == null || pageSize < 1 || pageSize > 40) {
            throw new BusinessException("每页条数合法范围1~40");
        }
        Integer offset = (currentPage - 1) * pageSize;
        List<CourseChoiceVO> courseChoiceVOs = courseChoiceMapper.getAvailableCourseVOsByName(offset, pageSize,courseName);
        Set<Long> selectedCourseIdSet = new HashSet<>(courseChoiceMapper.getChosenCourseIdList(studentId));
        courseChoiceVOs.forEach(vo -> vo.setChosen(selectedCourseIdSet.contains(vo.getCourseId())));
        return courseChoiceVOs;

    }

    @Override
    public Long getTotalCountByName(String courseName) {
        Long totalCount = courseChoiceMapper.getTotalCountByName(courseName);
        if (totalCount == null || totalCount < 0) {
            return 0L;
        }
        return totalCount;
    }
}




