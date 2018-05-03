package com.shadowxz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadowxz.dao.HomeworkScoreMapper;
import com.shadowxz.domain.HomeworkScore;
import com.shadowxz.service.HomeworkScoreService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/3 19:08
 * @Modified by:
 */
@Service
public class HomeworkScoreServiceImpl implements HomeworkScoreService {

    Logger logger = LoggerFactory.getLogger(HomeworkProgressServiceImpl.class);

    @Autowired
    HomeworkScoreMapper homeworkScoreMapper;

    @Override
    public void addHomeworkScores(List<HomeworkScore> scores) {
        try {
            homeworkScoreMapper.insertHomeworkScores(scores);
        } catch (Exception e) {
            logger.error("根据学号查询学生失败------------------->");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
