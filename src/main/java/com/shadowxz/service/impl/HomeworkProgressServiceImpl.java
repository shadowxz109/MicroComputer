package com.shadowxz.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadowxz.dao.HomeworkProgressMapper;
import com.shadowxz.domain.HomeworkProgress;
import com.shadowxz.service.HomeworkProgressService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/3 13:06
 * @Modified by:
 */

@Service
public class HomeworkProgressServiceImpl implements HomeworkProgressService {

    Logger logger = LoggerFactory.getLogger(HomeworkProgressServiceImpl.class);

    @Autowired
    HomeworkProgressMapper homeworkProgressMapper;

    @Override
    public void addHomeworkProgress(HomeworkProgress homeworkProgress) {
        try {
            homeworkProgressMapper.insertSelective(homeworkProgress);
        } catch (Exception e) {
            logger.error("新增作业进度失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifyHomeworkProgress(HomeworkProgress homeworkProgress) {
        try {
            homeworkProgressMapper.updateByPrimaryKeySelective(homeworkProgress);
        } catch (Exception e) {
            logger.error("修改作业进度失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<HomeworkProgress> findHPByHwIdAndStuId(Map<String, Object> map) {
        List<HomeworkProgress> list = null;
        try {
            list = homeworkProgressMapper.selectHPByHwIdAndStuId(map);
        } catch (Exception e) {
            logger.error("根据作业id和学生id作业进度失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void deleteHPByStudentId(Map<String, Object> map) {
        //todo
    }
}
