package com.example.demo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.example.demo.entity.vo.CourseChoiceVO;
import com.example.demo.entity.vo.Page;
import com.example.demo.entity.vo.Result;
import com.example.demo.service.CourseChoiceService;
import com.example.demo.service.impl.CourseChoiceServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SaCheckLogin
@RequestMapping("/courseChoice")
@RestController
public class CourseChoiceController {
    private final CourseChoiceService courseChoiceSeriveImpl;

    public CourseChoiceController(CourseChoiceServiceImpl courseChoiceSeriveImpl) {
        this.courseChoiceSeriveImpl = courseChoiceSeriveImpl;
    }

    @GetMapping("/getCourseChoicePage")
    public Result getCourseChoicePage(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        Long studentId = StpUtil.getLoginIdAsLong();
        List<CourseChoiceVO> courseChoiceVOList = courseChoiceSeriveImpl.getCourseChoiceVOList(studentId, currentPage, pageSize);
        Long totalCount = courseChoiceSeriveImpl.getTotalCount();
        Page<List<CourseChoiceVO>> courseChoicePage = new Page<>(currentPage, pageSize, totalCount, courseChoiceVOList);
        return Result.success("成功查询到选课列表", courseChoicePage);
    }

    @PostMapping("/chooseCourse")
    public Result selectCourse(@RequestParam Long courseId) {
        Long studentId = StpUtil.getLoginIdAsLong();
        courseChoiceSeriveImpl.chooseCourse(studentId, courseId);
        return Result.successMsg("选课成功");
    }

    @DeleteMapping("/dropCourse")
    public Result dropCourse(@RequestParam Long courseId) {
        Long studentId = StpUtil.getLoginIdAsLong();
        courseChoiceSeriveImpl.dropCourse(studentId, courseId);
        return Result.successMsg("退选成功");
    }

    @GetMapping("/getChosenCourseList")
    public Result getChosenCoureseList() {
        Long studentId = StpUtil.getLoginIdAsLong();
        List<CourseChoiceVO> chosenCourseList = courseChoiceSeriveImpl.getChosenCourseList(studentId);
        return Result.success("成功获取已选课数据", chosenCourseList);
    }

}
