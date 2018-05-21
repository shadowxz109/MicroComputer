package com.shadowxz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadowxz.dao.HomeworkItemMapper;
import com.shadowxz.domain.HomeworkItem;
import com.shadowxz.service.HomeworkItemService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/2 22:04
 * @Modified by:
 */


@Service
public class HomeworkItemServiceImpl implements HomeworkItemService{

    Logger logger = LoggerFactory.getLogger(HomeworkServiceImpl.class);

    @Autowired
    HomeworkItemMapper homeworkItemMapper;

    @Override
    public void deleteByHomeworkId(Integer homeworkId) {
        try {
            homeworkItemMapper.deleteByHomeworkId(homeworkId);
        } catch (Exception e) {
            logger.error("根据作业id删除作业题详情失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifyHomeworkItem(HomeworkItem homeworkItem) {
        try {
            homeworkItemMapper.updateByPrimaryKeySelective(homeworkItem);
        } catch (Exception e) {
            logger.error("修改作业题失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addHomeworkItem(HomeworkItem homeworkItem) {
        try {
            homeworkItemMapper.insertSelective(homeworkItem);
        } catch (Exception e) {
            logger.error("新增作业题失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public HomeworkItem findHomeworkItemById(Integer id) {
        HomeworkItem result = null;
        try {
            result = homeworkItemMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("根据id查询作业题失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void deleteHomeworkItemById(Integer id) {
        try {
            homeworkItemMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("删除作业题失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
