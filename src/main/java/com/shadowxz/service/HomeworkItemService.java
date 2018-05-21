package com.shadowxz.service;

import com.shadowxz.domain.HomeworkItem;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/2 22:02
 * @Modified by:
 */
public interface HomeworkItemService {

    void deleteByHomeworkId(Integer homeworkId);

    void deleteHomeworkItemById(Integer id);

    void addHomeworkItem(HomeworkItem homeworkItem);

    void modifyHomeworkItem(HomeworkItem homeworkItem);

    HomeworkItem findHomeworkItemById(Integer id);
}
