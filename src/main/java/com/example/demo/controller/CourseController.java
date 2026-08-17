package com.example.demo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.demo.entity.dto.CourseDTO;
import com.example.demo.entity.po.Course;
import com.example.demo.entity.vo.CourseVO;
import com.example.demo.entity.vo.Page;
import com.example.demo.entity.vo.Result;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.CourseService;
import com.example.demo.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SaCheckLogin
@RequestMapping("/course")
@RestController
public class CourseController {
    @Autowired
    private final CourseService courseServiceImpl;

    public CourseController(CourseServiceImpl courseServiceImpl) {
        this.courseServiceImpl = courseServiceImpl;
    }

    @PutMapping("add")
    public Result add(@RequestBody CourseDTO courseDTO) throws Exception {
        if (courseDTO == null
        ) {
            throw new BusinessException("传入参数为空");
        }
        Long id = courseServiceImpl.getMaxId();
        if (id == null) {
            id = 1L;
        } else {
            id += 1;
        }
        courseDTO.setId(id);

        courseServiceImpl.add(courseDTO);
        return Result.successMsg("添加课程成功");

    }

    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam Long id) throws Exception {
        if (id == null) {
            throw new BusinessException("参数异常");
        }
        courseServiceImpl.deleteById(id);
        return Result.successMsg("删除课程成功");
    }

    @PutMapping("/update")
    public Result update(@RequestBody CourseDTO courseDTO) throws Exception {
        if (courseDTO == null) {
            throw new BusinessException("参数异常");
        }

        courseServiceImpl.update(courseDTO);
        return Result.successMsg("课程修改成功");
    }

    @GetMapping("/getInfo")
    public Result getInfo(@RequestParam Long id) throws Exception {
        if (id == null) {
            throw new BusinessException("参数异常");
        }
        CourseVO courseVO = courseServiceImpl.getInfo(id);
        return Result.success("成功获取课程信息", courseVO);
    }

    @GetMapping("/getPage")
    public Result getPage(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        if (currentPage <= 0 || currentPage == null) {
            currentPage = 1;
        }
        if (pageSize > 1000) {
            pageSize = 1000;
        } else if (pageSize < 1) {
            pageSize = 1;
        }

        List<CourseVO> courseVOList = courseServiceImpl.getPage(currentPage, pageSize);
        Long totalCount = courseServiceImpl.getCount();
        Page coursePage = new Page(currentPage, pageSize, totalCount, courseVOList);

        return Result.success("成功获取课程页", coursePage);
    }


}
