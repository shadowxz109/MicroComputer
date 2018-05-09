package com.shadowxz.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shadowxz.domain.Constant;
import com.shadowxz.domain.Notify;
import com.shadowxz.service.NotifyService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/8 15:47
 * @Modified by:
 */

@RequestMapping("/notify")
@RestController()
public class NotifyController {

    @Autowired
    NotifyService notifyService;

    Logger logger = LoggerFactory.getLogger(NotifyController.class);

    @RequestMapping(value = "/{notifyId}",method = RequestMethod.GET)
    public Map<String,Object> getNotify(@PathVariable("notifyId") Integer notifyId, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            result.put("notify",notifyService.findNotifyById(notifyId));
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            String errMsg = "获取通知失败";
            logger.error(errMsg,e);
            result.put("msg_no",Constant.GET_DATA_ERR);
            result.put("msg",errMsg);
        }
        return result;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Map<String,Object> getNotify(HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            HttpSession session = request.getSession();
            Object sessionId = session.getAttribute("studentId") != null ? session.getAttribute("studentId") :
                    session.getAttribute("teacherId");
            if(sessionId  != null){
                result.put("notifies",notifyService.findAllNotify());
                result.put("msg_no",Constant.GET_DATA_SUCC);
            }else{
                result.put("msg_no",Constant.LOGIN_CODE_NOLOG);
                result.put("msg","请先登录");
            }
        } catch (Exception e) {
            String errMsg = "获取通知失败";
            logger.error(errMsg,e);
            result.put("msg_no",Constant.GET_DATA_ERR);
            result.put("msg",errMsg);
        }
        return result;
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public Map<String,Object> postNotify(HttpServletRequest request, Notify notify, @RequestParam("Clazz") String clazzs){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Object teacherId = request.getSession().getAttribute("teacherId");
            if(teacherId != null) {
                notify.setPulishTime(new Date());
                notify.setTeacherId(teacherId.toString());
                notifyService.addNotify(notify,clazzs);
                result.put("notify", notify);
                result.put("msg_no", Constant.GET_DATA_SUCC);
            }else{
                result.put("msg_no",Constant.LOGIN_CODE_NOLOG);
                result.put("msg","请先登录");
            }
        } catch (Exception e) {
            String errMsg = "新增通知失败";
            logger.error(errMsg,e);
            result.put("msg_no",Constant.GET_DATA_ERR);
            result.put("msg",errMsg);
        }
        return result;
    }

    @RequestMapping(value = "/{notifyId}",method = RequestMethod.PUT)
    public Map<String,Object> putNotify(@PathVariable("notify") Integer notifyId, Notify notify,HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Object teacherId = request.getSession().getAttribute("teacherId");
            if(teacherId != null) {
                notifyService.modifyNotify(notify);
                result.put("msg_no", Constant.GET_DATA_SUCC);
            }else{
                result.put("msg_no",Constant.LOGIN_CODE_NOLOG);
                result.put("msg","请先登录");
            }
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            String errMsg = "修改通知失败";
            logger.error(errMsg,e);
            result.put("msg_no",Constant.GET_DATA_ERR);
            result.put("msg",errMsg);
        }
        return result;
    }

    @RequestMapping(value = "/{notifyId}",method = RequestMethod.DELETE)
    public Map<String,Object> deleteNotify(@PathVariable("notify") Integer notifyId, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Object teacherId = request.getSession().getAttribute("teacherId");
            if(teacherId != null) {
                notifyService.deleteNotifyById(notifyId);
                result.put("msg_no", Constant.GET_DATA_SUCC);
            }else{
                result.put("msg_no",Constant.LOGIN_CODE_NOLOG);
                result.put("msg","请先登录");
            }
        } catch (Exception e) {
            String errMsg = "删除通知失败";
            logger.error(errMsg,e);
            result.put("msg_no",Constant.GET_DATA_ERR);
            result.put("msg",errMsg);
        }
        return result;
    }
}
