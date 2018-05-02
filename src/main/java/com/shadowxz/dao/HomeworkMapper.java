package com.shadowxz.dao;

import org.apache.ibatis.annotations.Mapper;

import com.shadowxz.domain.Homework;
@Mapper
public interface HomeworkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Homework record);

    int insertSelective(Homework record);

    Homework selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Homework record);

    int updateByPrimaryKey(Homework record);
}