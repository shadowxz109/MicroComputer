package com.shadowxz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.shadowxz.domain.Homework;

@Component
@Mapper
public interface HomeworkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Homework record);

    int insertSelective(Homework record);

    Homework selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Homework record);

    int updateByPrimaryKey(Homework record);

    Homework selectHomeDetailById(Integer id);

    Homework selectHwScoresByStuId(String studentId);

    Homework selectStuScoreByHwIdAndStuId(Map<String,Object> map);

    List<Homework> selectStuScoreByStatusAndStuId(Map<String,Object> map);

    Homework selectHwScoreByStuIdAndHwId(Map<String,Object> map);

    List<Homework> selectHomeworkByTeacherId(String teacherId);

    List<Map<String,Object>> selectClazzAverageScore(String clazz);

    List<Map<String,Object>> selectQuestionAverageScore(String clazz);

    List<Map<String,Object>> selectAverageScore();

    List<Map<String,Object>> selectClazzMinAndMaxScore(String clazz);

    List<Map<String,Object>> selectClazzScore(String clazz);

    List<Map<String,Object>> selectStudentScore(String studentId);

    List<Map<String,Object>> selectQuestionScore(String studentId);
}