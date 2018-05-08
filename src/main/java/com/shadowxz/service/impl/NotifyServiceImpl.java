package com.shadowxz.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shadowxz.dao.MessageMapper;
import com.shadowxz.dao.NotifyMapper;
import com.shadowxz.dao.StudentMapper;
import com.shadowxz.domain.Message;
import com.shadowxz.domain.Notify;
import com.shadowxz.domain.Student;
import com.shadowxz.service.NotifyService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/8 15:45
 * @Modified by:
 */
@Service
public class NotifyServiceImpl implements NotifyService{

    Logger logger = LoggerFactory.getLogger(NotifyServiceImpl.class);

    @Autowired
    NotifyMapper notifyMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    MessageMapper messageMapper;

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void addNotify(Notify notify,String clazzs) {
        try {
            notifyMapper.insert(notify);
            String[] array = clazzs.split(",");
            List<Student> students = studentMapper.selectStudentByClazzs(new ArrayList<>(Arrays.asList(array)));
            List<Message> messages = new ArrayList<>(students.size());
            for (Student student : students){
                Message message = new Message("0",notify.getId(),"0",student.getStudentId(),"");
                messages.add(message);
            }
            messageMapper.insertMessages(messages);
        } catch (Exception e) {
            logger.error("新增通知失败",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifyNotify(Notify notify) {
        notifyMapper.updateByPrimaryKeySelective(notify);
    }

    @Override
    public List<Notify> findAllNotify() {
        return notifyMapper.selectAllNotifies();
    }

    @Override
    public Notify findNotifyById(Integer id) {
        return notifyMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void deleteNotifyById(Integer id) {
        try {
            notifyMapper.deleteByPrimaryKey(id);
            Map<String,Object> map = new HashMap<>(2);
            map.put("type","1");
            map.put("contentId",id);
            messageMapper.deleteByTypeAndContentId(map);
        } catch (Exception e) {
            logger.error("删除通知失败",e);
            throw new RuntimeException(e);
        }
    }

}

