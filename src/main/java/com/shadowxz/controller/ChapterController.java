package com.shadowxz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shadowxz.domain.Chapter;
import com.shadowxz.domain.Constant;
import com.shadowxz.service.ChapterService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:43
 * @Modified by:
 */

@Controller
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    ChapterService chapterService;

    Logger logger = LoggerFactory.getLogger(CoursewareController.class);

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> addChapter(@RequestBody Chapter chapter, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if(request.getSession().getAttribute("teacherId") != null){
                chapterService.addChapter(chapter);
                result.put("msg","新增章节成功");
                result.put("msg_no",Constant.GET_DATA_SUCC);
            }else{
                result.put("msg","请先登录教师帐号");
                result.put("msg_no",Constant.GET_DATA_ERR);
            }
        } catch (Exception e) {
            String err = "新增章节信息失败";
            logger.error(err,e);
            result.put("msg",err);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/{chapterId}",method = RequestMethod.PUT)
    public @ResponseBody Map<String,Object> modifyChapter(@RequestBody Chapter chapter, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if(request.getSession().getAttribute("teacherId") != null){
                chapterService.modifyChapter(chapter);
                result.put("msg","修改章节成功");
                result.put("msg_no",Constant.GET_DATA_SUCC);
            }
        } catch (Exception e) {
            String err = "修改章节信息失败";
            logger.error(err,e);
            result.put("msg",err);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }


    @RequestMapping(value = "/{chapterId}",method = RequestMethod.DELETE)
    public @ResponseBody Map<String,Object> deleteChapter(@PathVariable("chapterId") String chapterId, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if(request.getSession().getAttribute("teacherId") != null){
                chapterService.deleteChapterById(chapterId);
                result.put("msg_no",Constant.GET_DATA_SUCC);
            }
        } catch (Exception e) {
            logger.error("修改章节信息失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }


    @RequestMapping(value = "/list/{fatherId}",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> findChildrenChapter(@PathVariable("fatherId") String fatherId){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            List<Chapter> list = chapterService.findChilByFatherId(fatherId);
            result.put("chapters",list);
            result.put("msg_no",Constant.GET_DATA_SUCC);
        } catch (Exception e) {
            logger.error("查询子节点章节信息失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> findAllChapters(HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if(request.getSession().getAttribute("teacherId") != null) {
                List<Chapter> list = chapterService.findAllChapters();
                result.put("chapters", list);
                result.put("msg_no", Constant.GET_DATA_SUCC);
            }
        } catch (Exception e) {
            logger.error("查询所有章节信息失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }
}
