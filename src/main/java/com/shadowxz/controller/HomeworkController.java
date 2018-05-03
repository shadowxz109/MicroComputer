package com.shadowxz.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shadowxz.domain.Constant;
import com.shadowxz.domain.Homework;
import com.shadowxz.domain.HomeworkScore;
import com.shadowxz.service.HomeworkItemService;
import com.shadowxz.service.HomeworkScoreService;
import com.shadowxz.service.HomeworkService;
import com.shadowxz.service.StudentService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:44
 * @Modified by:
 */

@Controller
@RequestMapping("/homework")
public class HomeworkController {

    Logger logger = LoggerFactory.getLogger(HomeworkController.class);

    @Autowired
    HomeworkService homeworkService;

    @Autowired
    HomeworkItemService homeworkItemService;

    @Autowired
    HomeworkScoreService homeworkScoreService;

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/{homeworkId}",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getHomeworkDetail(@PathVariable("homeworkId") int homeworkId){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Homework homework = homeworkService.findDetailByHomeworkId(homeworkId);
            result.put("data",homework);
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            logger.error("获取作业详情失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/{homeworkId}/clazz/{clazz}",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getClassHomeworkScore(@PathVariable("homeworkId") int homeworkId,
                                                             @PathVariable("clazz") String clazz){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Map<String,Object> map = new HashMap<>(2);
            map.put("homeworkId",homeworkId);
            map.put("clazz",clazz);
            Homework homework = homeworkService.findScoresByIdAndClazz(map);
            result.put("data",homework);
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            logger.error("获取班级作业成绩失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }


    @RequestMapping(value = "/student/{studentId}",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getStudHomeworkScore(@PathVariable("studentId") String studentId){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Homework homework = homeworkService.findScoresByStudentId(studentId);
            result.put("data",homework);
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            logger.error("获取学号作业成绩失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }


    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> addHomework(Homework homework, @RequestParam("clazzs") String clazzs, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if (request.getSession().getAttribute("teacherId") != null) {
                if(clazzs != null){
                    String[] clazz = clazzs.split(",");
                    List<String> studentIds = studentService.findStudentIdByClass(new ArrayList<>(Arrays.asList(clazz)));
                    homework.setPulishTime(new Date());
                    homeworkService.addHomework(homework);
                    int homeworkId = homework.getId();
                    HomeworkScore score = null;
                    List<HomeworkScore> scores = new ArrayList<>(studentIds.size());
                    for (int i = 0; i < studentIds.size() ; i++) {
                        score  = new HomeworkScore(homeworkId,studentIds.get(i),0.0f,"0");
                        scores.add(score);
                    }
                    homeworkScoreService.addHomeworkScores(scores);
                    result.put("data",homework);
                    result.put("msg_no",Constant.GET_DATA_SUCC);
                }
            }
        } catch (Exception e) {
            logger.error("新增作业信息失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/{urlHomeworkId}",method = RequestMethod.DELETE)
    public @ResponseBody Map<String,Object> deleteHomework(@PathVariable("urlHomeworkId") int urlHomeworkId,HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if (request.getSession().getAttribute("teacherId") != null) {
                homeworkService.deleteHomeworkById(urlHomeworkId);
                homeworkItemService.deleteByHomeworkId(urlHomeworkId);
                result.put("msg_no",Constant.GET_DATA_SUCC);
            }
        } catch (Exception e) {
            logger.error("删除作业信息失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }


    @RequestMapping(value = "/{urlHomeworkId}",method = RequestMethod.PUT)
    public @ResponseBody Map<String,Object> modifyHomework(HttpServletRequest request, Homework homework){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if (request.getSession().getAttribute("teacherId") != null) {
                homeworkService.modifyHomework(homework);
                result.put("msg_no",Constant.GET_DATA_SUCC);
            }
        } catch (Exception e) {
            logger.error("修改作业信息失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

}
