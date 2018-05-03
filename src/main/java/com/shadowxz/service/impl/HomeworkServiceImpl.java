package com.shadowxz.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadowxz.dao.HomeworkMapper;
import com.shadowxz.domain.Homework;
import com.shadowxz.service.HomeworkService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:39
 * @Modified by:
 */
@Service
public class HomeworkServiceImpl implements HomeworkService{

    Logger logger = LoggerFactory.getLogger(HomeworkServiceImpl.class);

    @Autowired
    HomeworkMapper homeworkMapper;

    @Override
    public Homework findDetailByHomeworkId(Integer id) {
        Homework homework = null;
        try {
            homework = homeworkMapper.selectHomeDetailById(id);
        } catch (Exception e) {
            logger.error("根据id查询作业详情失败------------------->");
            e.printStackTrace();
            throw new RuntimeException();
        }
        return homework;
    }

    @Override
    public void addHomework(Homework homework) {
        try {
            homeworkMapper.insertSelective(homework);
        } catch (Exception e) {
            logger.error("插入作业信息失败------------------->");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteHomeworkById(Integer id) {
        try {
            homeworkMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("删除作业信息失败------------------->");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void modifyHomework(Homework homework) {
        try {
            homeworkMapper.updateByPrimaryKeySelective(homework);
        } catch (Exception e) {
            logger.error("修改作业信息失败------------------->");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public Homework findScoresByIdAndClazz(Map<String, Object> map) {
        Homework homework = null;
        try {
            homework = homeworkMapper.selectHwScoresByIdAndClazz(map);
        } catch (Exception e) {
            logger.error("根据作业id和班级查询作业成绩失败------------------->");
            e.printStackTrace();
            throw new RuntimeException();
        }
        return homework;
    }

    @Override
    public Homework findScoresByStudentId(String studentId) {
        Homework homework = null;
        try {
            homework = homeworkMapper.selectHwScoresByStuId(studentId);
        } catch (Exception e) {
            logger.error("根据学号查询作业成绩失败------------------->");
            e.printStackTrace();
            throw new RuntimeException();
        }
        return homework;
    }
}
