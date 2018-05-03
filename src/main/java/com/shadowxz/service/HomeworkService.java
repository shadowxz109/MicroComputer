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

    Homework findScoresByStudentId(String studentId);

    Homework findScoresByIdAndClazz(Map<String,Object> map);

    Homework findDetailByHomeworkId(Integer id);

    void addHomework(Homework homework);

    void deleteHomeworkById(Integer id);

    void modifyHomework(Homework homework);
}
