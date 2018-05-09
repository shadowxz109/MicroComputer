package com.shadowxz.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shadowxz.dao.HomeworkScoreMapper;
import com.shadowxz.domain.Constant;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/9 17:01
 * @Modified by:
 */

@RequestMapping("/score")
@RestController
public class HomeworkScoreController {

    Logger logger = LoggerFactory.getLogger(HomeworkScoreController.class);

    @Autowired
    HomeworkScoreMapper homeworkScoreMapper;

    @RequestMapping(value = "/{homeworkId}/student/{studentId}")
    public Map<String,Object> getHomeworkScore(@PathVariable("homeworkId") Integer homeworkId,
                                               @PathVariable("studentId") String studentId){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        Map<String,Object> map = new HashMap<>(2);
        map.put("studentId",studentId);
        map.put("homeworkId",homeworkId);
        try {
            result.put("score",homeworkScoreMapper.selectByStuIdAndHwId(map));
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            String errMsg = "获取个人作业成绩失败";
            logger.error(errMsg,e);
            result.put("msg_no",Constant.GET_DATA_ERR);
            result.put("msg",errMsg);
        }
        return result;
    }

}
