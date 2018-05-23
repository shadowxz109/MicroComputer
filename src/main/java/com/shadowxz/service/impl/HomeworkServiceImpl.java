package com.shadowxz.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shadowxz.dao.HomeworkItemMapper;
import com.shadowxz.dao.HomeworkMapper;
import com.shadowxz.dao.HomeworkScoreMapper;
import com.shadowxz.dao.MessageMapper;
import com.shadowxz.dao.StudentMapper;
import com.shadowxz.domain.Homework;
import com.shadowxz.domain.HomeworkScore;
import com.shadowxz.domain.Message;
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

    @Autowired
    StudentMapper studentMapper;
    
    @Autowired
    HomeworkScoreMapper homeworkScoreMapper;
    
    @Autowired
    MessageMapper messageMapper;

    @Autowired
    HomeworkItemMapper homeworkItemMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addHomework(Homework homework,String clazzs) {
        try {
            homeworkMapper.insertSelective(homework);
            String[] array = clazzs.split(",");
            List<String> studentIds = studentMapper.selectStudentIdByClazzs(new ArrayList<>(Arrays.asList(array)));
            List<HomeworkScore> scores = new ArrayList<>(studentIds.size());
            for (int i = 0; i < studentIds.size() ; i++) {
                HomeworkScore score  = new HomeworkScore(homework.getId(),studentIds.get(i),0.0f,"0");
                scores.add(score);
            }
            homeworkScoreMapper.insertHomeworkScores(scores);
            List<Message> messages = new ArrayList<>(studentIds.size());
            for (int i = 0; i < studentIds.size() ; i++) {
                Message message = new Message("1",homework.getId(),"0",studentIds.get(i),"");
                messages.add(message);
            }
            messageMapper.insertMessages(messages);
        } catch (Exception e) {
            logger.error("插入作业信息失败------------------->",e);
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteHomeworkById(Integer id) {
        try {
            Map<String,Object> map = new HashMap<>(2);
            map.put("type","2");
            map.put("contentId",id);
            messageMapper.deleteByTypeAndContentId(map);
            homeworkItemMapper.deleteByHomeworkId(id);
            homeworkMapper.deleteByPrimaryKey(id);
            homeworkScoreMapper.deleteByHomeworkId(id);
        } catch (Exception e) {
            logger.error("删除作业信息失败------------------->",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifyHomework(Homework homework) {
        try {
            homeworkMapper.updateByPrimaryKeySelective(homework);
        } catch (Exception e) {
            logger.error("修改作业信息失败------------------->",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Homework findByHomeworkId(Integer id) {
        Homework homework = null;
        try {
            homework = homeworkMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("查询作业信息失败------------------->",e);
            throw new RuntimeException(e);
        }
        return homework;
    }

    //某班学生单次作业完成情况
    @Override
    public List<HomeworkScore> findScoresByIdAndClazz(Map<String, Object> map) {
        List<HomeworkScore> homework = null;
        try {
            homework = homeworkScoreMapper.selectHwScoresByIdAndClazz(map);
        } catch (Exception e) {
            logger.error("根据作业id和班级查询作业成绩失败------------------->",e);
            throw new RuntimeException(e);
        }
        return homework;
    }

    @Override
    public Homework findHwScoreByStuIdAndHwId(Map<String, Object> map) {
        Homework homework = null;
        try {
            homework = homeworkMapper.selectHwScoreByStuIdAndHwId(map);
        } catch (Exception e) {
            logger.error("查询作业信息失败------------------->",e);
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
//            logger.error("根据学号查询作业成绩失败------------------->",e);
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
            logger.error("根据作业id和学生id查询作业详情失败------------------->",e);
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
            logger.error("根据id查询作业详情失败------------------->",e);
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
            logger.error("根据学号查询作业完成情况失败------------------->",e);
            throw new RuntimeException(e);
        }
        return homework;
    }

    @Override
    public List<Homework> findHomeworkByTeacherId(String teacherId) {
        List<Homework> homeworks = null;
        try {
            homeworks = homeworkMapper.selectHomeworkByTeacherId(teacherId);
        } catch (Exception e) {
            logger.error("根据教师号查询作业------------------->",e);
            throw new RuntimeException(e);
        }
        return homeworks;
    }
}
