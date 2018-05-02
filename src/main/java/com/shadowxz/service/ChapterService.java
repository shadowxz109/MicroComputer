package com.shadowxz.service;

import java.util.List;

import com.shadowxz.domain.Chapter;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:29
 * @Modified by:
 */
public interface ChapterService {

    Chapter findChapterById(String id);

    void deleteChapterById(String id);

    void addChapter(Chapter chapter);

    void modifyChapter(Chapter chapter);

    List<Chapter> findChilByFatherId(String fatherId);

    List<Chapter> findAllChapters();
}
