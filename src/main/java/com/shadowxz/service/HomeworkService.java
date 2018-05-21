package com.shadowxz.service;

import java.util.List;
import java.util.Map;

import com.shadowxz.domain.Homework;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:37
 * @Modified by:
 */
public interface HomeworkService {

    List<Homework> findScoresByIdAndClazz(Map<String,Object> map);

    Homework findStuScoreByHwIdAndStuId(Map<String,Object> map);

    Homework findDetailByHomeworkId(Integer id);

    Homework findByHomeworkId(Integer id);

    Homework findHwScoreByStuIdAndHwId(Map<String,Object> map);

    List<Homework> findStuScoreByStutasAndStuId(Map<String,Object> map);

    List<Homework> findHomeworkByTeacherId(String teacherId);

    void addHomework(Homework homework,String clazzs);

    void deleteHomeworkById(Integer id);

    void modifyHomework(Homework homework);
}
