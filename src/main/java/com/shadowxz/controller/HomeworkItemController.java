package com.shadowxz.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shadowxz.domain.Constant;
import com.shadowxz.domain.HomeworkItem;
import com.shadowxz.service.HomeworkItemService;
import com.shadowxz.util.LogUtil;

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
            result = LogUtil.errorLog(result,"获取题目失败",e);
        }
        return result;
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> addHomeworkItem(@RequestBody HomeworkItem homeworkItem, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if (request.getSession().getAttribute("teacherId") != null) {
                homeworkItemService.addHomeworkItem(homeworkItem);
                result.put("homeworkItem",homeworkItem);
                result.put("msg","添加题目成功");
                result.put("msg_no",Constant.GET_DATA_SUCC);
            }else {
                result.put("msg","请先登录");
                result.put("msg_no",Constant.LOGIN_CODE_NOLOG);
            }
        } catch (Exception e) {
            result = LogUtil.errorLog(result,"新增题目失败",e);
        }
        return result;
    }

    @RequestMapping(value = "/{homeworkItemId}",method = RequestMethod.DELETE)
    public @ResponseBody Map<String,Object> deleteHomeworkItem(@PathVariable("homeworkItemId") int homeworkItemId,HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if (request.getSession().getAttribute("teacherId") != null) {
                homeworkItemService.deleteHomeworkItemById(homeworkItemId);
                result.put("msg_no",Constant.GET_DATA_SUCC);
                result.put("msg","删除题目成功");
            }
        } catch (Exception e) {
            result = LogUtil.errorLog(result,"删除题目失败",e);
        }
        return result;
    }


    @RequestMapping(value = "/{urlHomeworkId}",method = RequestMethod.PUT)
    public @ResponseBody Map<String,Object> modifyHomeworkItem(HttpServletRequest request, @RequestBody HomeworkItem homeworkItem){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if (request.getSession().getAttribute("teacherId") != null) {
                homeworkItemService.modifyHomeworkItem(homeworkItem);
                result.put("msg_no",Constant.GET_DATA_SUCC);
                result.put("msg","修改题目成功");
            }
        } catch (Exception e) {
            result = LogUtil.errorLog(result,"修改题目失败",e);
        }
        return result;
    }
}
