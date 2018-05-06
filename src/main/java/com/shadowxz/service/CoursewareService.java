package com.shadowxz.service;

import java.util.List;

import com.shadowxz.domain.Courseware;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:30
 * @Modified by:
 */
public interface CoursewareService {

    void addCourseware(Courseware courseware);

    void addDownloadTimes(Integer id);

    Courseware findCoursewareById(Integer id);

    List<Courseware> findCoursewareByChapterId(String chapterId);

    void deleteCoursewares(Integer id);

    List<Courseware> findAllCoursewares();
}
