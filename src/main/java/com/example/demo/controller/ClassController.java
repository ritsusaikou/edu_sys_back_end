package com.example.demo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.demo.entity.dto.ClassDTO;
import com.example.demo.entity.vo.ClassVO;
import com.example.demo.entity.vo.Page;
import com.example.demo.entity.vo.Result;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.ClassService;
import com.example.demo.service.impl.ClassServiceImpl;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SaCheckLogin
@RequestMapping("/class")
@RestController
public class ClassController {
    private final ClassService classServiceImpl;

    @Autowired
    public ClassController(ClassServiceImpl classServiceImpl) {
        this.classServiceImpl = classServiceImpl;
    }

    @PutMapping("/add")
    public Result add(@RequestBody ClassDTO classDTO) throws Exception {
        if (classDTO == null) {
            throw new BusinessException("传入参数为空");
        }
        Long id = classServiceImpl.getMaxId();
        if (id == null) {
            id = 1L;
        } else {
            id += 1;
        }
        classDTO.setId(id);
        classServiceImpl.add(classDTO);
        return Result.successMsg("添加班级成功");
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam Long id) throws Exception {
        if (id == null
                || id == 0L) {
            throw new BusinessException("参数id异常");
        }
        classServiceImpl.deleteById(id);
        return Result.successMsg("删除班级成功");
    }

    @PutMapping("/update")
    public Result update(@RequestBody ClassDTO classDTO) throws Exception {
        if (classDTO == null
                || classDTO.getId() == null) {
            throw new BusinessException("参数异常");
        }
        classServiceImpl.update(classDTO);
        return Result.successMsg("修改班级成功");
    }

    @GetMapping("/getInfo")
    public Result getInfo(@RequestParam Long id) throws Exception {
        if (id == null
                || id == 0L) {
            throw new BusinessException("参数id异常");
        }
        ClassVO classVO = classServiceImpl.getInfo(id);
        return Result.success("成功获取班级信息", classVO);
    }

    @GetMapping("/getPage")
    public Result getPage(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        if (currentPage == null || currentPage <= 0) {
            currentPage = 1;
        }
        if (pageSize > 1000) {
            pageSize = 1000;
        } else if (pageSize < 1) {
            pageSize = 1;
        }

        List<ClassVO> classVOList = classServiceImpl.getPage(currentPage, pageSize);
        Long totalCount = classServiceImpl.getCount();
        Page classPage = new Page(currentPage, pageSize, totalCount, classVOList);
        return Result.success("成功获取班级页", classPage);
    }


}
