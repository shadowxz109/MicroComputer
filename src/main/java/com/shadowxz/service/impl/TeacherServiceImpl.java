package com.shadowxz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadowxz.dao.TeacherMapper;
import com.shadowxz.domain.Teacher;
import com.shadowxz.service.TeacherService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:41
 * @Modified by:
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public String findPasswordByTeacherId(String teacherId) {
        String result = null;
        try {
            result = teacherMapper.selectPasswordByTeacherId(teacherId);
        } catch (Exception e) {
            logger.error("根据教师编号查询密码失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result ;
    }

    @Override
    public Teacher findTeacherById(String teacherId) {
        Teacher teacher = null;
        try {
            teacher = teacherMapper.selectByPrimaryKey(teacherId);
        } catch (Exception e) {
            logger.error("根据教师编号查询教师失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return teacher;
    }
}
