package com.shadowxz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.shadowxz.domain.Courseware;

@Component
@Mapper
public interface CoursewareMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Courseware record);

    int insertSelective(Courseware record);

    Courseware selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Courseware record);

    int updateByPrimaryKey(Courseware record);

    int addDownloadTimes(Integer id);

    List<Courseware> selectByChapterId(String chapterId);
}