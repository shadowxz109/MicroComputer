package com.shadowxz.dao;

import com.shadowxz.domain.HomeworkProgress;

public interface HomeworkProgressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HomeworkProgress record);

    int insertSelective(HomeworkProgress record);

    HomeworkProgress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HomeworkProgress record);

    int updateByPrimaryKey(HomeworkProgress record);
}