package com.shadowxz.service;

import com.shadowxz.domain.Homework;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:37
 * @Modified by:
 */
public interface HomeworkService {

    Homework findDetailByHomeworkId(Integer id);

    void addHomework(Homework homework);
}
