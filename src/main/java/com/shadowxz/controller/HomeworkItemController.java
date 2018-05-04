package com.shadowxz.controller;

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
import com.shadowxz.domain.HomeworkItem;
import com.shadowxz.service.HomeworkItemService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/3 10:04
 * @Modified by:
 */

@Controller
@RequestMapping("/homeworkItem")
public class HomeworkItemController {
    
    Logger logger = LoggerFactory.getLogger(HomeworkItemController.class);

    @Autowired
    HomeworkItemService homeworkItemService;

    @RequestMapping(value = "/{homeworkItemId}",method = RequestMethod.GET)
    public @ResponseBody
    Map<String,Object> getHomeworkItem(@PathVariable("homeworkItemId") int homeworkItemId){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            HomeworkItem homeworkItem = homeworkItemService.findHomeworkItemById(homeworkItemId);
            result.put("data",homeworkItem);
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            logger.error("获取作业题题详情失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> addHomeworkItem(HomeworkItem homeworkItem, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if (request.getSession().getAttribute("teacherId") != null) {
                homeworkItemService.addHomeworkItem(homeworkItem);
                result.put("data",homeworkItem);
                result.put("msg_no",Constant.GET_DATA_SUCC);
            }else {
                result.put("msg","请先登录");
                result.put("msg_no",Constant.LOGIN_CODE_NOLOG);
            }
        } catch (Exception e) {
            logger.error("新增作业题信息失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/{urlHomeworkId}",method = RequestMethod.DELETE)
    public @ResponseBody Map<String,Object> deleteHomeworkItem(@PathVariable("urlHomeworkId") int urlHomeworkId,HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if (request.getSession().getAttribute("teacherId") != null) {
                homeworkItemService.deleteByHomeworkId(urlHomeworkId);
                result.put("msg_no",Constant.GET_DATA_SUCC);
            }
        } catch (Exception e) {
            logger.error("删除作业题信息失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }


    @RequestMapping(value = "/{urlHomeworkId}",method = RequestMethod.PUT)
    public @ResponseBody Map<String,Object> modifyHomeworkItem(HttpServletRequest request, HomeworkItem homeworkItem){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if (request.getSession().getAttribute("teacherId") != null) {
                homeworkItemService.modifyHomeworkItem(homeworkItem);
                result.put("msg_no",Constant.GET_DATA_SUCC);
            }
        } catch (Exception e) {
            logger.error("修改作业题信息失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }
}
