package com.shadowxz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shadowxz.domain.HomeworkClazz;

@Mapper
public interface HomeworkClazzMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HomeworkClazz record);

    int insertSelective(HomeworkClazz record);

    int inertHomeworkClazzs(List<HomeworkClazz> list);

    HomeworkClazz selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HomeworkClazz record);

    int updateByPrimaryKey(HomeworkClazz record);
}