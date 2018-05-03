package com.shadowxz.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.shadowxz.domain.HomeworkItem;

@Component
@Mapper
public interface HomeworkItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HomeworkItem record);

    int insertSelective(HomeworkItem record);

    HomeworkItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HomeworkItem record);

    int updateByPrimaryKey(HomeworkItem record);

    int deleteByHomeworkId(Integer homeworkId);

}