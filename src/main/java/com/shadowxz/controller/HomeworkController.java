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
import com.shadowxz.util.LogUtil;

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
    public @ResponseBody Map<String,Object> getHomeworkDetail(@PathVariable("homeworkId") int homeworkId,HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Homework homework = homeworkService.findDetailByHomeworkId(homeworkId);
            if(request.getSession().getAttribute("teacherId") == null){
                List<HomeworkItem> items = homework.getItems();
                for(HomeworkItem item : items){
                    item.setAnswer(null);
                }
            }
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
            List<HomeworkScore> homeworks = homeworkService.findScoresByIdAndClazz(map);
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

    @RequestMapping(value = "/list/teacher/{teacherId}",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getTeacherHomeworks(@PathVariable("teacherId") String teacherId){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            List<Homework> homeworks = homeworkService.findHomeworkByTeacherId(teacherId);
            result.put("homeworks",homeworks);
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            String err = "获取老师所有作业";
            logger.error(err,e);
            result.put("msg",err);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/{homeworkId}/student/{urlStudentId}",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> postStudentHomeworkScoreDetail(@PathVariable("homeworkId") int homeworkId,
                                                                          @PathVariable("urlStudentId") String urlStudentId,
                                                                           HttpServletRequest request,
                                                                           @RequestBody Homework homework){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Object studentId = request.getSession().getAttribute("studentId");
            Object teacherId = request.getSession().getAttribute("teacherId");
            Map<String,Object> map = new HashMap<>(3);
            map.put("studentId",urlStudentId);
            map.put("homeworkId",homeworkId);
            map.put("id",homeworkId);
            //包含参考答案的题目
            List<HomeworkItem> answerItems = homeworkService.findStuScoreByHwIdAndStuId(map).getItems();
            //防止学生修改截至日期
            Homework realHomework = homeworkService.findByHomeworkId(homeworkId);
            List<HomeworkItem> items = homework.getItems();
            List<HomeworkProgress> progresses = new ArrayList<>(items.size());
            HomeworkScore homeworkScore = homeworkScoreService.findByStuIdAndHwId(map);
            if(studentId != null && urlStudentId.equals(studentId.toString())){
                if(realHomework.getDeadline().after(new Date())){
                    boolean allChoice = true;
                    if(items != null && items.size() != 0){
                        for(int i = 0; i < items.size(); i++ ){
                            HomeworkItem item = items.get(i);
                            HomeworkProgress progress  = items.get(i).getHomeworkProgress();
                            if(progress != null){
                                //自动批改
                                if ( item.getType().equals("1") ) {
                                    if(answerItems.get(i).getAnswer().equals(progress.getAnswer())){
                                        progress.setScore(answerItems.get(i).getScore());
                                    }else{
                                        progress.setScore(0.0f);
                                    }
                                }else if(allChoice && (!item.getType().equals("1"))){
                                    allChoice = false;
                                }
                                progress.setType("0");
                                progress.setStatus("2");
                                progress.setStudentId(studentId.toString());
                                progress.setFinishDate(new Date());
                                progresses.add(progress);
                            }
                        }
                        homeworkProgressService.addHomeworkProgresss(progresses);
                        homeworkScore.setStatus("1");
                        if(allChoice){
                            homeworkScore.setStatus("2");
                        }
                    }
                }
                homeworkScoreService.modifyHomeworkScore(homeworkScore);
                result.put("msg_no",Constant.GET_DATA_SUCC);
                result.put("msg","提交成功");
            }else if(teacherId  != null){
                if(items != null && items.size() != 0){
                    for(HomeworkItem item : items){
                        HomeworkProgress progress  = item.getHomeworkProgress();
                        if(progress != null){
                            progress.setStatus("2");
                            progresses.add(progress);
                        }
                    }
                    homeworkProgressService.modifyHomeworkProgress(progresses);
                    homeworkScore.setStatus("2");
                }
                homeworkScoreService.modifyHomeworkScore(homeworkScore);
                result.put("msg_no",Constant.GET_DATA_SUCC);
                result.put("msg","批改成功");
            }else{
                result.put("msg_no",Constant.GET_DATA_ERR);
                result.put("msg","请先登录");
            }
        } catch (Exception e) {
            LogUtil.errorLog(result,"提交失败",e);
        }
        return result;
    }

    @RequestMapping(value = "/{homeworkId}/student/{studentId}",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getStudentHomeworkScoreDetail(@PathVariable("homeworkId") int homeworkId,
                                                                  @PathVariable("studentId") String studentId){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Map<String,Object> map = new HashMap<>(3);
            map.put("id",homeworkId);
            map.put("studentId",studentId);
            map.put("homeworkId",homeworkId);
            Homework homework = homeworkService.findStuScoreByHwIdAndStuId(map);
            HomeworkScore score = homeworkScoreService.findByStuIdAndHwId(map);
            if(score != null && score.getStatus().equals("0")){
                List<HomeworkItem> items = homework.getItems();
                for(HomeworkItem item : items){
                    item.setAnswer(null);
                }
            }
            result.put("homework",homework);
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            LogUtil.errorLog(result,"获取学生单次作业成绩失败",e);
        }
        return result;
    }

    @RequestMapping(value = "/score/{homeworkId}/student/{studentId}",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getStudentHomeworkScore(@PathVariable("homeworkId") int homeworkId,
                                                                    @PathVariable("studentId") String studentId){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Map<String,Object> map = new HashMap<>(2);
            map.put("homeworkId",homeworkId);
            map.put("studentId",studentId);
            Homework homework = homeworkService.findHwScoreByStuIdAndHwId(map);
            result.put("homework",homework);
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            LogUtil.errorLog(result,"获取学生单次作业成绩失败",e);
        }
        return result;
    }


    @RequestMapping(value = "",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> addHomework(@RequestBody Homework homework, @RequestParam("clazzs") String clazzs, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if (request.getSession().getAttribute("teacherId") != null) {
                if(clazzs != null){
                    homework.setPulishTime(new Date());
                    homeworkService.addHomework(homework,clazzs);
                    result.put("homework",homework);
                    result.put("msg_no",Constant.GET_DATA_SUCC);
                    result.put("msg","新增作业信息成功");
                }
            }
        } catch (Exception e) {
            LogUtil.errorLog(result,"新增作业信息失败",e);
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
                result.put("msg","删除成功");
            }
        } catch (Exception e) {
            result = LogUtil.errorLog(result,"删除作业信息失败",e);
        }
        return result;
    }


    @RequestMapping(value = "/{urlHomeworkId}",method = RequestMethod.PUT)
    public @ResponseBody Map<String,Object> modifyHomework(HttpServletRequest request, @RequestBody Homework homework){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if (request.getSession().getAttribute("teacherId") != null) {
                homeworkService.modifyHomework(homework);
                result.put("msg_no",Constant.GET_DATA_SUCC);
            }
        } catch (Exception e) {
            result = LogUtil.errorLog(result,"修改作业信息失败",e);
        }
        return result;
    }

}
