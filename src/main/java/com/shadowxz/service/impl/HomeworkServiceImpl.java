package com.shadowxz.service.impl;

import java.math.BigDecimal;
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

import com.shadowxz.dao.HomeworkClazzMapper;
import com.shadowxz.dao.HomeworkItemMapper;
import com.shadowxz.dao.HomeworkMapper;
import com.shadowxz.dao.HomeworkScoreMapper;
import com.shadowxz.dao.MessageMapper;
import com.shadowxz.dao.StudentMapper;
import com.shadowxz.domain.Homework;
import com.shadowxz.domain.HomeworkClazz;
import com.shadowxz.domain.HomeworkScore;
import com.shadowxz.domain.Message;
import com.shadowxz.domain.QuestionAverageScore;
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

    @Autowired
    HomeworkClazzMapper homeworkClazzMapper;

    @Override
    public Map<String,List<Object>> findClazzAverageScore(String clazz){
        Map<String,List<Object>> resultMap = new HashMap<>(2);
        try {
            List<Map<String,Object>> resultList = homeworkMapper.selectClazzAverageScore(clazz);
            List<Object> descList = new ArrayList<>(resultList.size());
            List<Object> avgList = new ArrayList<>(resultList.size());
            for (Map<String,Object> map : resultList){
                for(Map.Entry<String,Object> entry : map.entrySet()){
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if("avgScore".equals(key)){
                        avgList.add(value);
                    }else if("description".equals(key)){
                        descList.add(value);
                    }
                }
            }
            resultMap.put("score",avgList);
            resultMap.put("description",descList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return resultMap;
    }

    @Override
    public List<QuestionAverageScore> findQuestionAverageScore(String clazz){
        List<QuestionAverageScore>  resultList = new ArrayList<>();
        try {
            List<Map<String,Object>> list = homeworkMapper.selectQuestionAverageScore(clazz);
            trans(list,resultList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return resultList;
    }

    private List<QuestionAverageScore> trans(List<Map<String,Object>> list,List<QuestionAverageScore> resultList){
        QuestionAverageScore questionAverageScore = new QuestionAverageScore();
        String description = "";
        boolean homeworkEnd = false;//是否某次作业结束
        List<Double> avgList = new ArrayList<>();
        List<String> questionNumList = new ArrayList<>();
        for (Map<String,Object> map : list){
            for(Map.Entry<String,Object> entry : map.entrySet()){
                Object key = entry.getKey();
                Object value = entry.getValue();
                if("score".equals(key)){
                    if(value instanceof Float){
                        avgList.add(((Float)value).doubleValue());
                    }else{
                        avgList.add(((BigDecimal)value).doubleValue());
                    }
                }else if("description".equals(key)){
                    if(!description.equals(value.toString())){
                        if(!description.equals("")){
                            homeworkEnd = true;
                        }else{
                            questionAverageScore.setDescription(value.toString());
                        }
                        description = value.toString();
                    }
                }else if("questionNum".equals(key)){
                    questionNumList.add(value.toString());
                }
            }
            if(homeworkEnd){
                double lastScore = avgList.remove(avgList.size()-1);
                String lastQuestionNum = questionNumList.remove(questionNumList.size()-1);
                questionAverageScore.setQuestionNumList(questionNumList);
                questionAverageScore.setScoreList(avgList);
                resultList.add(questionAverageScore);
                questionAverageScore = new QuestionAverageScore();
                questionAverageScore.setDescription(description);
                avgList = new ArrayList<>();
                avgList.add(lastScore);
                questionNumList = new ArrayList<>();
                questionNumList.add(lastQuestionNum);
                homeworkEnd = false;
            }
        }
        questionAverageScore.setQuestionNumList(questionNumList);
        questionAverageScore.setScoreList(avgList);
        resultList.add(questionAverageScore);
        return resultList;
    }

    public Map<String,List<Object>> findAverageScore(){
        Map<String,List<Object>> resultMap = new HashMap<>(3);
        try {
            List<Map<String,Object>> resultList = homeworkMapper.selectAverageScore();
            List<Object> descList = new ArrayList<>(resultList.size());
            List<Object> avgList = new ArrayList<>(resultList.size());
            List<Object> clazzList = new ArrayList<>(resultList.size());
            for (Map<String,Object> map : resultList){
                for(Map.Entry<String,Object> entry : map.entrySet()){
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if("avgScore".equals(key)){
                        avgList.add(value);
                    }else if("description".equals(key)){
                        descList.add(value);
                    }else if("clazz".equals(key)){
                        clazzList.add(value);
                    }
                }
            }
            resultMap.put("score",avgList);
            resultMap.put("description",descList);
            resultMap.put("clazz",clazzList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return resultMap;
    }

    public Map<String,List<Object>> findClazzMinAndMaxScore(String clazz){
        Map<String,List<Object>> resultMap = new HashMap<>(3);
        try {
            List<Map<String,Object>> resultList = homeworkMapper.selectClazzMinAndMaxScore(clazz);
            List<Object> descList = new ArrayList<>(resultList.size());
            List<Object> minList = new ArrayList<>(resultList.size());
            List<Object> maxList = new ArrayList<>(resultList.size());
            for (Map<String,Object> map : resultList){
                for(Map.Entry<String,Object> entry : map.entrySet()){
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if("minScore".equals(key)){
                        minList.add(value);
                    }else if("maxScore".equals(key)){
                        maxList.add(value);
                    }else if("description".equals(key)){
                        descList.add(value);
                    }
                }
            }
            resultMap.put("minScore",minList);
            resultMap.put("maxScore",maxList);
            resultMap.put("description",descList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return resultMap;
    }

    public Map<String,Object> findClazzScore(String clazz){
        Map<String,Object> resultMap = new HashMap<>(5);
        try {
            List<Map<String,Object>> resultList = homeworkMapper.selectClazzScore(clazz);
            int failed = 0,qualified = 0,good = 0,excellent= 0;
            String description = "";
            for (Map<String,Object> map : resultList){
                for(Map.Entry<String,Object> entry : map.entrySet()){
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if("scores".equals(key)){
                        float score = (float)value;
                        if(score < 60){
                            failed++;
                        }else if(score >= 60 && score < 80){
                            qualified++;
                        }else if(score >=80 && score < 90){
                            good++;
                        }else if(score >= 90){
                            excellent++;
                        }
                    }else if(description.equals("") && "description".equals(key)){
                        resultMap.put("description",value.toString());
                    }
                }
            }
            resultMap.put("failed",failed);
            resultMap.put("qualified",qualified);
            resultMap.put("good",good);
            resultMap.put("excellent",excellent);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return resultMap;
    }

    public List<QuestionAverageScore> findQuestionScore(String studentId){
        List<QuestionAverageScore>  resultList = new ArrayList<>();
        try {
            List<Map<String,Object>> list = homeworkMapper.selectQuestionScore(studentId);
            trans(list,resultList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return resultList;
    }

    public Map<String,List<Object>> findStudentScore(String studentId){
        Map<String,List<Object>> resultMap = new HashMap<>(2);
        try {
            List<Map<String,Object>> resultList = homeworkMapper.selectStudentScore(studentId);
            List<Object> descList = new ArrayList<>(resultList.size());
            List<Object> avgList = new ArrayList<>(resultList.size());
            for (Map<String,Object> map : resultList){
                for(Map.Entry<String,Object> entry : map.entrySet()){
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if("scores".equals(key)){
                        avgList.add(value);
                    }else if("description".equals(key)){
                        descList.add(value);
                    }
                }
            }
            resultMap.put("scores",avgList);
            resultMap.put("description",descList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addHomework(Homework homework,String clazzs) {
        try {
            homeworkMapper.insertSelective(homework);
            String[] array = clazzs.split(",");
            ArrayList<HomeworkClazz> list = new ArrayList<>(array.length);
            for(String clazz : array){
                list.add(new HomeworkClazz(clazz,homework.getId()));
            }
            homeworkClazzMapper.inertHomeworkClazzs(list);
            List<String> studentIds = studentMapper.selectStudentIdByClazzs(new ArrayList<>(Arrays.asList(array)));
            List<HomeworkScore> scores = new ArrayList<>(studentIds.size());
            for (int i = 0; i < studentIds.size() ; i++) {
                scores.add(new HomeworkScore(homework.getId(),studentIds.get(i),0.0f,"0"));
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
            homework = homeworkMapper.selectStuScoreByStatusAndStuId(map);
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
