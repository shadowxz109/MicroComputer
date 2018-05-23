package com.shadowxz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.shadowxz.domain.HomeworkScore;

@Component
@Mapper
public interface HomeworkScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByHomeworkId(Integer homeworkId);

    int insert(HomeworkScore record);

    int insertSelective(HomeworkScore record);

    HomeworkScore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HomeworkScore record);

    int updateByPrimaryKey(HomeworkScore record);

    int insertHomeworkScores(List<HomeworkScore> scores);

    int updateOverdue();

    HomeworkScore selectByStuIdAndHwId(Map<String,Object> map);

    List<HomeworkScore> selectHwScoresByIdAndClazz(Map<String,Object> map);
}