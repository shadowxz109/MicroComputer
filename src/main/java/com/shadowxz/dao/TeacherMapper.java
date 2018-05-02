package com.shadowxz.dao;

import org.apache.ibatis.annotations.Mapper;

import com.shadowxz.domain.Teacher;

@Mapper
public interface TeacherMapper {
    int deleteByPrimaryKey(String teacherId);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(String teacherId);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);
}