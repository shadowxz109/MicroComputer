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

    List<Homework> selectHwScoresByIdAndClazz(Map<String,Object> map);

    Homework selectHwScoresByStuId(String studentId);

    Homework selectStuScoreByHwIdAndStuId(Map<String,Object> map);

    List<Homework> selectStuScoreByStutasAndStuId(Map<String,Object> map);

    Homework selectHwScoreByStuIdAndHwId(Map<String,Object> map);

    List<Homework> selectHomeworkByTeacherId(String teacherId);
}