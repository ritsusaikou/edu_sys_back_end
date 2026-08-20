package com.example.demo.mapper;

import com.example.demo.entity.po.Score;
import com.example.demo.entity.vo.CourseScoreVO;
import com.example.demo.entity.vo.ScoreVO;
import org.apache.ibatis.annotations.Mapper;
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

    List<CourseScoreVO> getCourseScores(@Param("courseId") Long courseId);

    Long getMaxId();

    void initEmptyScore(@Param("scoreId") Long scoreId, @Param("studentId") Long studentId, @Param("courseId") Long courseId);

    Score getScore(@Param("courseId") Long courseId, @Param("studentId") Long studentId);

    void deleteScore(@Param("courseId") Long courseId, @Param("studentId") Long studentId);

    void update(@Param("scoreId") Long scoreId, @Param("score") String score);
}




