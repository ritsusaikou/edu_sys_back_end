package com.example.demo.service.impl;

import com.example.demo.entity.po.Score;
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

    public ScoreServiceImpl(ScoreMapper scoreMapper) {
        this.scoreMapper = scoreMapper;
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
}




