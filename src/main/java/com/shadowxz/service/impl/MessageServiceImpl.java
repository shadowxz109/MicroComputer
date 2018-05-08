package com.shadowxz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadowxz.dao.MessageMapper;
import com.shadowxz.domain.Message;
import com.shadowxz.service.MessageService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/8 16:25
 * @Modified by:
 */

@Service
public class MessageServiceImpl implements MessageService {

    Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    MessageMapper messageMapper;

    @Override
    public List<Message> findMessagesByReceiveId(String receiveId) {
        List<Message> messages = null;
        try {
            messages = messageMapper.selectByReceiveId(receiveId);
        } catch (Exception e) {
            logger.error("根据接收者查询消息失败",e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void addMessages(List<Message> messages) {
        try {
            messageMapper.insertMessages(messages);
        } catch (Exception e) {
            logger.error("批量新增消息失败",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addMessage(Message message) {
        try {
            messageMapper.insert(message);
        } catch (Exception e) {
            logger.error("新增消息失败",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateMessageStaById(Integer id) {
        try {
            messageMapper.updateMessageStaById(id);
        } catch (Exception e) {
            logger.error("更新消息失败",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int findMsgCountByReceiveId(String receiveId) {
        try {
            return messageMapper.selectNoMsgCountByReceiveId(receiveId);
        } catch (Exception e) {
            logger.error("获取未读消息失败",e);
            throw new RuntimeException(e);
        }
    }
}
