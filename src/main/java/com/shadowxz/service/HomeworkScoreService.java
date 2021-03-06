package com.shadowxz.service;

import java.util.List;
import java.util.Map;

import com.shadowxz.domain.HomeworkScore;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/3 19:08
 * @Modified by:
 */
public interface HomeworkScoreService {

    void addHomeworkScores(List<HomeworkScore> scores);

    HomeworkScore findByStuIdAndHwId(Map<String,Object> map);

    void modifyHomeworkScore(HomeworkScore score);

}
