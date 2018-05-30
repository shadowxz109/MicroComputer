package com.shadowxz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shadowxz.domain.Constant;
import com.shadowxz.domain.Message;
import com.shadowxz.service.MessageService;
import com.shadowxz.util.LogUtil;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/8 15:48
 * @Modified by:
 */
@RequestMapping("/message")
@RestController()
public class MessageController {

    Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @RequestMapping(value = "/list/{receiveId}",method = RequestMethod.GET)
    public Map<String,Object> getMessageByReceiveId(@PathVariable("receiveId") String receiveId, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            List<Message> messages = messageService.findMessagesByReceiveId(receiveId);
            result.put("messages",messages);
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            String errMsg = "查询消息失败";
            logger.error(errMsg,e);
            result.put("msg_no",Constant.GET_DATA_ERR);
            result.put("msg",errMsg);
        }
        return result;
    }

    @RequestMapping(value = "/count/{receiveId}",method = RequestMethod.GET)
    public Map<String,Object> getMessageCountByReceiveId(@PathVariable("receiveId") String receiveId, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Integer count = messageService.findMsgCountByReceiveId(receiveId);
            result.put("count",count);
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            String errMsg = "查询未读消息失败";
            logger.error(errMsg,e);
            result.put("msg_no",Constant.GET_DATA_ERR);
            result.put("msg",errMsg);
        }
        return result;
    }

    @RequestMapping(value = "/{messageId}",method = RequestMethod.PUT)
    public Map<String,Object> modifyMessageByReceiveId(@PathVariable("messageId") Integer messageId, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            HttpSession session = request.getSession();
            Object sessionId = session.getAttribute("studentId") != null ? session.getAttribute("studentId") :
                    session.getAttribute("teacherId");
            if(sessionId  != null){
                messageService.updateMessageStaById(messageId);
                result.put("msg_no",Constant.GET_DATA_SUCC);
            }else{
                result.put("msg_no",Constant.GET_DATA_ERR);
                result.put("msg","请先登录");
            }
        } catch (Exception e) {
            String errMsg = "修改消息状态失败";
            LogUtil.errorLog(result,errMsg,e);
        }
        return result;
    }


}
