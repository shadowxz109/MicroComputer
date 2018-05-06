package com.shadowxz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadowxz.dao.CoursewareMapper;
import com.shadowxz.domain.Courseware;
import com.shadowxz.service.CoursewareService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:42
 * @Modified by:
 */
@Service
public class CoursewareServiceImpl implements CoursewareService{

    Logger logger = LoggerFactory.getLogger(CoursewareService.class);

    @Autowired
    CoursewareMapper coursewareMapper;

    @Override
    public void addCourseware(Courseware courseware) {
        try {
           coursewareMapper.insert(courseware);
        } catch (Exception e) {
            logger.error("新增课件信息失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public void addDownloadTimes(Integer id) {
        try {
            coursewareMapper.addDownloadTimes(id);
        } catch (Exception e) {
            logger.error("更新课件下载次数失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public Courseware findCoursewareById(Integer id) {
        Courseware courseware = null;
        try {
            courseware = coursewareMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("查询课件下载次数失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return courseware;
    }

    @Override
    public List<Courseware> findCoursewareByChapterId(String chapterId) {
        List<Courseware> list = null;
        try {
            list  = coursewareMapper.selectByChapterId(chapterId);
        } catch (Exception e) {
            logger.error("根据章节id查询失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void deleteCoursewares(Integer id) {
        try {
            coursewareMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("删除课件下载失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Courseware> findAllCoursewares() {
        List<Courseware> list = null;
        try {
            list  = coursewareMapper.selectAllCoursewares();
        } catch (Exception e) {
            logger.error("查询所有课件失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;
    }
}
