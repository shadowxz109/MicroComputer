package com.shadowxz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.shadowxz.domain.HomeworkScore;

@Component
@Mapper
public interface HomeworkScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HomeworkScore record);

    int insertSelective(HomeworkScore record);

    HomeworkScore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HomeworkScore record);

    int updateByPrimaryKey(HomeworkScore record);

    int insertHomeworkScores(List<HomeworkScore> scores);

    int updateOverdue();
}