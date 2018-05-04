package com.shadowxz.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shadowxz.domain.Constant;
import com.shadowxz.domain.HomeworkProgress;
import com.shadowxz.service.HomeworkProgressService;
import com.shadowxz.service.HomeworkService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/3 21:58
 * @Modified by:
 */
@Controller
public class HomeworkProgressController {

    Logger logger = LoggerFactory.getLogger(HomeworkProgressController.class);

    @Autowired
    HomeworkProgressService homeworkProgressService;

    @Autowired
    HomeworkService homeworkService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> addHomeworkProgress(HomeworkProgress homeworkProgress, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        Object studentId = request.getSession().getAttribute("studentId");
        try {
            if(studentId != null){
                homeworkProgress.setStudentId(studentId.toString());
                homeworkProgress.setStatus("0");
                homeworkProgress.setFinishDate(new Date());
                homeworkProgress.setScore(0.0f);
                homeworkProgressService.addHomeworkProgress(homeworkProgress);
            }
        } catch (Exception e) {
            logger.error("新增作业进度失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/{progressId}",method = RequestMethod.PUT)
    public @ResponseBody Map<String,Object> modifyHomeworkProgress(HomeworkProgress homeworkProgress, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if(request.getSession().getAttribute("teacherId") != null){
                homeworkProgress.setStatus("2");
                homeworkProgressService.modifyHomeworkProgress(homeworkProgress);
            }else if(request.getSession().getAttribute("studentId") != null){
                homeworkProgress.setScore(0.0f);
                homeworkProgress.setFinishDate(new Date());
                if(Integer.parseInt(homeworkProgress.getStatus()) >= 2 ){
                    homeworkProgress.setStatus("1");
                }
                homeworkProgressService.modifyHomeworkProgress(homeworkProgress);
            }
            result.put("data",homeworkProgress);
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            logger.error("修改作业进度失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/{progressId}",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getHomeworkProgress(@PathVariable("progressId") Integer progressId, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            HomeworkProgress homeworkProgress = homeworkProgressService.findHPById(progressId);
        } catch (Exception e) {
            logger.error("获取作业进度失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }
}
