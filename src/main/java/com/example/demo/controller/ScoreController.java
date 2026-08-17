package com.example.demo.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.example.demo.entity.vo.Result;
import com.example.demo.entity.vo.ScoreVO;
import com.example.demo.service.ScoreService;
import com.example.demo.service.impl.ScoreServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SaCheckLogin
@RequestMapping("/score")
@RestController
public class ScoreController {

    private final ScoreService scoreServiceImpl;

    public ScoreController(ScoreServiceImpl scoreServiceImpl) {
        this.scoreServiceImpl = scoreServiceImpl;
    }

    @GetMapping("/getScoreList")
    public Result getScoreList() {
        Long studentId = StpUtil.getLoginIdAsLong();
        List<ScoreVO> scoreList = scoreServiceImpl.getScoreList(studentId);
        return Result.success("成功获取成绩表", scoreList);
    }


}
