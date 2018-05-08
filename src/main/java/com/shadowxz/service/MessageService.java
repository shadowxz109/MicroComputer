package com.shadowxz.service;

import java.util.List;

import com.shadowxz.domain.Message;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/8 15:45
 * @Modified by:
 */
public interface MessageService {

    List<Message> findMessagesByReceiveId(String receiveId);

    void addMessages(List<Message> messages);

    void addMessage(Message message);

    void updateMessageStaById(Integer id);

    int findMsgCountByReceiveId(String receiveId);
}
