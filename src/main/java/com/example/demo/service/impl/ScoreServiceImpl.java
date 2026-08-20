package com.example.demo.service.impl;

import com.example.demo.entity.po.Score;
import com.example.demo.entity.vo.CourseScoreVO;
import com.example.demo.entity.vo.CourseVO;
import com.example.demo.entity.vo.ScoreVO;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.ScoreService;
import com.example.demo.mapper.ScoreMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Ritsu
 * @description 针对表【score】的数据库操作Service实现
 * @createDate 2026-07-23 09:19:32
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreMapper scoreMapper;
    private final ScoreService scoreService;

    public ScoreServiceImpl(ScoreMapper scoreMapper, ScoreService scoreService) {
        this.scoreMapper = scoreMapper;
        this.scoreService = scoreService;
    }

    @Override
    public List<ScoreVO> getScoreList(Long studentId) {
        if (studentId == null || studentId <= 0) {
            throw new BusinessException("参数非法");
        }
        List<ScoreVO> scoreList = scoreMapper.getScoreList(studentId);
        return scoreList;
    }

    @Override
    public void updateScoreToMax(Long scoreId) {
        scoreMapper.updateScoreToMax(scoreId);
    }

    @Override
    public List<CourseScoreVO> getCourseScores(Long courseId) {
        if (courseId == null || courseId <= 0) {
            throw new BusinessException("参数非法");
        }
        List<CourseScoreVO> courseScores = scoreMapper.getCourseScores(courseId);
        return courseScores;
    }

    @Override
    public void initEmptyScore(Long studentId, Long courseId) {
        Long scoreId = scoreMapper.getMaxId();
        if (scoreId == null || scoreId <= 0) {
            scoreId = 1L;
        } else {
            scoreId += 1;
        }
        scoreMapper.initEmptyScore(scoreId, studentId, courseId);
    }

    @Override
    public void deleteScore(Long courseId, Long studentId) {
        scoreMapper.deleteScore(courseId, studentId);
    }

    @Override
    public void update(Long scoreId, String score) {
        if (scoreId == null || score == null || scoreId <= 0) {
            throw new BusinessException("参数非法");
        }
        scoreMapper.update(scoreId,score);
    }
}




