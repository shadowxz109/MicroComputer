package com.shadowxz.dao;

import java.util.List;
import java.util.Map;

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

    List<HomeworkProgress> selectHPByHwIdAndStuId(Map<String,Object> map);

    int insertHomeworkProgresss(List<HomeworkProgress> progresses);
}