package com.example.demo.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.example.demo.entity.vo.CourseScoreVO;
import com.example.demo.entity.vo.Result;
import com.example.demo.entity.vo.ScoreVO;
import com.example.demo.service.ScoreService;
import com.example.demo.service.impl.ScoreServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SaCheckLogin
@RequestMapping("/score")
@RestController
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(@Qualifier("scoreServiceImpl") ScoreService scoreService) {
        this.scoreService = scoreService;
    }


    @PostMapping("/update")
    public Result update(@RequestParam Long scoreId,Long score){
        String score1 = String.valueOf(score);
        scoreService.update(scoreId,score1);
        return Result.successMsg("修改成功");
    }


    @GetMapping("/getScoreList")
    public Result getScoreList() {
        Long studentId = StpUtil.getLoginIdAsLong();
        List<ScoreVO> scoreList = scoreService.getScoreList(studentId);
        return Result.success("成功获取成绩表", scoreList);
    }

    @GetMapping("/getCourseScores")
    public Result getCourseScores(@RequestParam Long courseId) {
        List<CourseScoreVO> courseScores = scoreService.getCourseScores(courseId);
        return Result.success("成功获取课程成绩数据",courseScores);
    }


}
