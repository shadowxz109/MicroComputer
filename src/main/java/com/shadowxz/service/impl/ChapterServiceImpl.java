package com.shadowxz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadowxz.dao.ChapterMapper;
import com.shadowxz.domain.Chapter;
import com.shadowxz.service.ChapterService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:38
 * @Modified by:
 */
@Service
public class ChapterServiceImpl implements ChapterService {

    Logger logger = LoggerFactory.getLogger(ChapterService.class);

    @Autowired
    ChapterMapper chapterMapper;

    @Override
    public Chapter findChapterById(String id) {
        Chapter chapter = null;
        try {
            chapter = chapterMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("查询章节信息失败------------------->");
            e.printStackTrace();
        }
        return chapter;
    }

    @Override
    public void deleteChapterById(String id) {
        try {
            chapterMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("删除章节信息失败------------------->");
            e.printStackTrace();
        }
    }

    @Override
    public void addChapter(Chapter chapter) {
        try {
            chapterMapper.insert(chapter);
        } catch (Exception e) {
            logger.error("新增章节信息失败------------------->");
            e.printStackTrace();
        }
    }

    @Override
    public void modifyChapter(Chapter chapter) {
        try {
            chapterMapper.updateByPrimaryKeySelective(chapter);
        } catch (Exception e) {
            logger.error("修改章节信息失败------------------->");
            e.printStackTrace();
        }
    }

    @Override
    public List<Chapter> findChilByFatherId(String fatherId) {
        List<Chapter> list = null;
        try {
            list = chapterMapper.selectChildByFatherId(fatherId);
        } catch (Exception e) {
            logger.error("查询子节点章节信息失败------------------->");
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Chapter> findAllChapters() {
        List<Chapter> list = null;
        try {
            list = chapterMapper.selectAllChapters();
        } catch (Exception e) {
            logger.error("查询所有章节信息失败------------------->");
            e.printStackTrace();
        }
        return list;
    }
}
