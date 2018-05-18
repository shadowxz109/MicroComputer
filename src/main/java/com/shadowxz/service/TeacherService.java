package com.shadowxz.service;

import com.shadowxz.domain.Teacher;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:37
 * @Modified by:
 */
public interface TeacherService {

    String findPasswordByTeacherId(String teacherId);

    Teacher findTeacherById(String teacherId);

}
