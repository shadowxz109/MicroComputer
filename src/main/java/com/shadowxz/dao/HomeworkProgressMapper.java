package com.shadowxz.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.shadowxz.domain.HomeworkProgress;

@Component
@Mapper
public interface HomeworkProgressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HomeworkProgress record);

    int insertSelective(HomeworkProgress record);

    HomeworkProgress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HomeworkProgress record);

    int updateByPrimaryKey(HomeworkProgress record);
}