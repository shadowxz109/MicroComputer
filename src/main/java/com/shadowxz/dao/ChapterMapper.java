package com.shadowxz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shadowxz.domain.Chapter;

@Mapper
public interface ChapterMapper {

    int deleteByPrimaryKey(String chapterId);

    int insert(Chapter record);

    int insertSelective(Chapter record);

    Chapter selectByPrimaryKey(String chapterId);

    int updateByPrimaryKeySelective(Chapter record);

    int updateByPrimaryKey(Chapter record);

    List<Chapter> selectChildByFatherId(String chapterId);

    List<Chapter> selectAllChapters();
}