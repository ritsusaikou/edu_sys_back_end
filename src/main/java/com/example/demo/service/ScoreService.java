package com.example.demo.service;

import com.example.demo.entity.vo.CourseScoreVO;
import com.example.demo.entity.vo.ScoreVO;

import java.util.List;

/**
 * @author Ritsu
 * @description 针对表【score】的数据库操作Service
 * @createDate 2026-07-23 09:19:32
 */
public interface ScoreService {

    List<ScoreVO> getScoreList(Long studentId);

    void updateScoreToMax(Long scoreId);

    List<CourseScoreVO> getCourseScores(Long courseId);

    void initEmptyScore(Long studentId, Long courseId);

    void deleteScore(Long courseId, Long studentId);

    void update(Long scoreId, String score1);
}
