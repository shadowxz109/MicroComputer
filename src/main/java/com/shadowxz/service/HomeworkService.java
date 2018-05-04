package com.shadowxz.service;

import java.util.Map;

import com.shadowxz.domain.Homework;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:37
 * @Modified by:
 */
public interface HomeworkService {

    Homework findScoresByIdAndClazz(Map<String,Object> map);

    Homework findStuScoreByHwIdAndStuId(Map<String,Object> map);

    Homework findDetailByHomeworkId(Integer id);

    Homework findByHomeworkId(Integer id);

    Homework findStuScoreByStutasAndStuId(Map<String,Object> map);

    void addHomework(Homework homework);

    void deleteHomeworkById(Integer id);

    void modifyHomework(Homework homework);
}
