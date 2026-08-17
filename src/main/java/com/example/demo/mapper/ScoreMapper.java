package com.example.demo.mapper;

import com.example.demo.entity.po.Score;
import com.example.demo.entity.vo.ScoreVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Ritsu
 * @description 针对表【score】的数据库操作Mapper
 * @createDate 2026-07-23 09:19:32
 * @Entity com.example.demo.entity.po.Score
 */
public interface ScoreMapper {

    List<ScoreVO> getScoreList(@Param("studentId") Long studentId);

    void updateScoreToMax(@Param("scoreId") Long scoreId);
}




