package com.shadowxz.service;

import java.util.List;
import java.util.Map;

import com.shadowxz.domain.HomeworkProgress;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/3 13:05
 * @Modified by:
 */
public interface HomeworkProgressService {

    List<HomeworkProgress> findHPByHwIdAndStuId(Map<String,Object> map);

    void addHomeworkProgress(HomeworkProgress homeworkProgress);

    void addHomeworkProgresss(List<HomeworkProgress> progresses);

    void deleteHPByStudentId(Map<String,Object> map);

    void modifyHomeworkProgress(HomeworkProgress homeworkProgress);

    HomeworkProgress findHPById(Integer id);

    void modifyHomeworkProgress(List<HomeworkProgress> homeworkProgresses);
}
