package com.shadowxz.service;

import java.util.List;
import java.util.Map;

import com.shadowxz.domain.Homework;
import com.shadowxz.domain.HomeworkScore;
import com.shadowxz.domain.QuestionAverageScore;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:37
 * @Modified by:
 */
public interface HomeworkService {

    List<HomeworkScore> findScoresByIdAndClazz(Map<String,Object> map);

    Homework findStuScoreByHwIdAndStuId(Map<String,Object> map);

    Homework findDetailByHomeworkId(Integer id);

    Homework findByHomeworkId(Integer id);

    Homework findHwScoreByStuIdAndHwId(Map<String,Object> map);

    List<Homework> findStuScoreByStutasAndStuId(Map<String,Object> map);

    List<Homework> findHomeworkByTeacherId(String teacherId);

    Map<String,List<Object>> findClazzAverageScore(String clazz);

    List<QuestionAverageScore> findQuestionAverageScore(String clazz);

    List<QuestionAverageScore> findQuestionScore(String studentId);

    Map<String,List<Object>> findStudentScore(String studentId);

    Map<String,List<Object>> findAverageScore();

    Map<String,List<Object>> findClazzMinAndMaxScore(String clazz);

    Map<String,Object> findClazzScore(String clazz);

    void addHomework(Homework homework,String clazzs);

    void deleteHomeworkById(Integer id);

    void modifyHomework(Homework homework);
}
