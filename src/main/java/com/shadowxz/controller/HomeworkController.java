package com.shadowxz.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shadowxz.domain.Constant;
import com.shadowxz.domain.Homework;
import com.shadowxz.domain.HomeworkItem;
import com.shadowxz.domain.HomeworkProgress;
import com.shadowxz.domain.HomeworkScore;
import com.shadowxz.service.HomeworkItemService;
import com.shadowxz.service.HomeworkProgressService;
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
    HomeworkProgressService homeworkProgressService;

    @Autowired
    HomeworkScoreService homeworkScoreService;

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/{homeworkId}",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getHomeworkDetail(@PathVariable("homeworkId") int homeworkId){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Homework homework = homeworkService.findDetailByHomeworkId(homeworkId);
            result.put("homework",homework);
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
            map.put("id",homeworkId);
            map.put("clazz",clazz);
            List<Homework> homeworks = homeworkService.findScoresByIdAndClazz(map);
            result.put("homeworks",homeworks);
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            logger.error("获取班级作业成绩失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/list/student/{studentId}",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getStudentHomeworkScore(@PathVariable("studentId") String studentId,
                                                                    @RequestParam("status") String status){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Map<String,Object> map = new HashMap<>(2);
            map.put("status",status);
            if(status.equals("all")){
                map.put("status",null);
            }
            map.put("studentId",studentId);
            List<Homework> homeworks = homeworkService.findStuScoreByStutasAndStuId(map);
            result.put("homeworks",homeworks);
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            logger.error("获取学生所有作业完成情况",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/{homeworkId}/student/{studentId}",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> postStudentHomeworkScoreDetail(@PathVariable("homeworkId") int homeworkId,
                                                                          @PathVariable("studentId") String studentId,
                                                                           HttpServletRequest request,
                                                                           @RequestBody Homework homework){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            List<HomeworkItem> items = homework.getItems();
            Homework realHomework = homeworkService.findByHomeworkId(homeworkId);
            List<HomeworkProgress> progresses = new ArrayList<>(items.size());
            if(realHomework.getDeadline().after(new Date())){
                if(items != null && items.size() != 0){
                    for(HomeworkItem item : items){
                        HomeworkProgress progress  = item.getHomeworkProgress();
                        if(progress != null){
                            if ((progress.getType() == null || progress.getType().equals("0") || progress.getType().equals("4")) && item.getAnswer().equals(progress.getAnswer())) {
                                progress.setScore(item.getScore());
                            }
                            progress.setType("0");
                            progress.setStatus("2");
                            progress.setStudentId(studentId);
                            progress.setFinishDate(new Date());
                            progresses.add(progress);
                        }
                    }
                    homeworkProgressService.addHomeworkProgresss(progresses);
                    Map<String,Object> map = new HashMap<>(2);
                    map.put("studentId",studentId);
                    map.put("homeworkId",homeworkId);
                    HomeworkScore homeworkScore = homeworkScoreService.findByStuIdAndHwId(map);
                    homeworkScore.setStatus("1");
                    homeworkScoreService.modifyHomeworkScore(homeworkScore);
                }
                result.put("msg_no",Constant.GET_DATA_SUCC);
            }else{
                result.put("msg_no",Constant.GET_DATA_ERR);
            }
        } catch (Exception e) {
            logger.error("提交学生单次作业失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/{homeworkId}/student/{studentId}",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getStudentHomeworkScoreDetail(@PathVariable("homeworkId") int homeworkId,
                                                                  @PathVariable("studentId") String studentId){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Map<String,Object> map = new HashMap<>(2);
            map.put("id",homeworkId);
            map.put("studentId",studentId);
            Homework homework = homeworkService.findStuScoreByHwIdAndStuId(map);
            result.put("homework",homework);
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            logger.error("获取学生单次作业成绩失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }


    @RequestMapping(value = "",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> addHomework(Homework homework, @RequestParam("clazzs") String clazzs, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if (request.getSession().getAttribute("teacherId") != null) {
                if(clazzs != null){
                    homework.setPulishTime(new Date());
                    homeworkService.addHomework(homework,clazzs);
                    result.put("homework",homework);
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
