package com.shadowxz.service;

import java.util.List;

import com.shadowxz.domain.Notify;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/8 15:46
 * @Modified by:
 */
public interface NotifyService {

    void addNotify(Notify notify,String clazzs);

    void modifyNotify(Notify notify);

    void deleteNotifyById(Integer id);

    //void findNotifyByTeaId(String teacherId);

    List<Notify> findAllNotify();

    Notify findNotifyById(Integer id);
}
