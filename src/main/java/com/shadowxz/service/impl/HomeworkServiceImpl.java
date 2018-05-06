package com.shadowxz.service.impl;

import java.util.List;
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
    public void addHomework(Homework homework) {
        try {
            homeworkMapper.insertSelective(homework);
        } catch (Exception e) {
            logger.error("插入作业信息失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteHomeworkById(Integer id) {
        try {
            homeworkMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("删除作业信息失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifyHomework(Homework homework) {
        try {
            homeworkMapper.updateByPrimaryKeySelective(homework);
        } catch (Exception e) {
            logger.error("修改作业信息失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Homework findByHomeworkId(Integer id) {
        Homework homework = null;
        try {
            homework = homeworkMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("查询作业信息失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return homework;
    }

    //某班学生单次作业完成情况
    @Override
    public List<Homework> findScoresByIdAndClazz(Map<String, Object> map) {
        List<Homework> homework = null;
        try {
            homework = homeworkMapper.selectHwScoresByIdAndClazz(map);
        } catch (Exception e) {
            logger.error("根据作业id和班级查询作业成绩失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return homework;
    }

//    @Override
//    public Homework findScoresByStudentId(String studentId) {
//        Homework homework = null;
//        try {
//            homework = homeworkMapper.selectHwScoresByStuId(studentId);
//        } catch (Exception e) {
//            logger.error("根据学号查询作业成绩失败------------------->");
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//        return homework;
//    }

    //某学生单次作业完成情况
    @Override
    public Homework findStuScoreByHwIdAndStuId(Map<String, Object> map) {
        Homework homework = null;
        try {
            homework = homeworkMapper.selectStuScoreByHwIdAndStuId(map);
        } catch (Exception e) {
            logger.error("根据作业id和学生id查询作业详情失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return homework;
    }

    //单次作业详情
    @Override
    public Homework findDetailByHomeworkId(Integer id) {
        Homework homework = null;
        try {
            homework = homeworkMapper.selectHomeDetailById(id);
        } catch (Exception e) {
            logger.error("根据id查询作业详情失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return homework;
    }

    //单个学生所有作业完成状态
    @Override
    public List<Homework> findStuScoreByStutasAndStuId(Map<String, Object> map) {
        List<Homework> homework = null;
        try {
            homework = homeworkMapper.selectStuScoreByStutasAndStuId(map);
        } catch (Exception e) {
            logger.error("根据学号查询作业完成情况失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return homework;
    }
}
