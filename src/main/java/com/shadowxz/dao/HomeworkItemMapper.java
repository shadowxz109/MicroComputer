package com.shadowxz.dao;

import com.shadowxz.domain.HomeworkItem;

public interface HomeworkItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HomeworkItem record);

    int insertSelective(HomeworkItem record);

    HomeworkItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HomeworkItem record);

    int updateByPrimaryKey(HomeworkItem record);
}