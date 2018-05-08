package com.shadowxz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.shadowxz.domain.Message;

@Component
@Mapper
public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    List<Message> selectByReceiveId(String receiveId);

    int insertMessages(List<Message> messages);

    int updateMessageStaById(Integer id);

    int selectNoMsgCountByReceiveId(String receiveId);

    int deleteByTypeAndContentId(Map<String,Object> map);
}