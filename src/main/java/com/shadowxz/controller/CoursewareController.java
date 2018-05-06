package com.shadowxz.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shadowxz.domain.Chapter;
import com.shadowxz.domain.Constant;
import com.shadowxz.domain.Courseware;
import com.shadowxz.service.ChapterService;
import com.shadowxz.service.CoursewareService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:43
 * @Modified by:
 */

@Controller
@RequestMapping("/courseware")
public class CoursewareController {

    Logger logger = LoggerFactory.getLogger(CoursewareController.class);

    @Autowired
    CoursewareService coursewareService;

    @Autowired
    ChapterService chapterService;

    @Value("${upload.dir}")
    String uploadDir;

    @Transactional
    @RequestMapping(value = "/download/{coursewareId}/{filename:.+}",method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadCourse(@PathVariable("coursewareId") int coursewareId,@PathVariable("filename") String filename,HttpServletRequest request){
        ResponseEntity<byte[]> result = null;
        try {
            Courseware courseware = coursewareService.findCoursewareById(coursewareId);
            if(courseware != null){
                String path = uploadDir + "/courseware/";
                File file = new File(path + File.separator + filename);
                HttpHeaders headers = new HttpHeaders();
                String downloadFielName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
                headers.setContentDispositionFormData("attachment", downloadFielName);
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                coursewareService.addDownloadTimes(coursewareId);
                result = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            logger.error("下载课件错误",e);
            e.printStackTrace();
        }
        return  result;
    }

    @RequestMapping(value = "/list/{chapterId}",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getCoursewareList(@PathVariable("chapterId") String chapterId){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            List<Courseware> coursewares = coursewareService.findCoursewareByChapterId(chapterId);
            result.put("msg_no", Constant.GET_DATA_SUCC);
            result.put("coursewares", coursewares);
        } catch (Exception e) {
            logger.error("查询课件列表失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getAllCoursewareList(){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            List<Courseware> coursewares = coursewareService.findAllCoursewares();
            result.put("msg_no", Constant.GET_DATA_SUCC);
            result.put("coursewares", coursewares);
        } catch (Exception e) {
            logger.error("查询课件列表失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/{chapterId}",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> uploadCourse(@PathVariable("chapterId") String chapterId, @RequestParam("file")MultipartFile file,
                                                         HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Object teacherId = request.getSession().getAttribute("teacherId");
            if(teacherId != null && file != null) {
                String filename = file.getOriginalFilename();
                String path = uploadDir + "/courseware/";
                File filepath = new File(path, filename);
                if (!filepath.getParentFile().exists()) {
                    filepath.getParentFile().mkdirs();
                }
                file.transferTo(new File(path + File.separator + filename));
                Courseware courseware = new Courseware();
                List<Courseware> coursewares = coursewareService.findCoursewareByChapterId(chapterId);
                Chapter chapter = chapterService.findChapterById(chapterId);
                if (coursewares != null && chapter != null) {
                    if (coursewares.size() == 0) {
                        courseware.setName(chapter.getChapterName());
                    } else {
                        courseware.setName(chapter.getChapterName() + "(" + coursewares.size() + ")");
                    }
                }
                courseware.setChapterId(chapterId);
                courseware.setFile(file.getOriginalFilename());
                courseware.setUploadDate(new Date());
                courseware.setDownloadTimes(0);
                courseware.setTeacherId(teacherId.toString());
                coursewareService.addCourseware(courseware);
                result.put("msg_no", Constant.GET_DATA_SUCC);
            }
        } catch (Exception e) {
            logger.error("上传课件失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/{coursewareId}",method = RequestMethod.DELETE)
    public @ResponseBody Map<String,Object> deleteCoureware(@PathVariable("coursewareId") int coursewareId,HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if(request.getSession().getAttribute("teacherId") != null) {
                coursewareService.deleteCoursewares(coursewareId);
                result.put("msg_no", Constant.GET_DATA_SUCC);
            }
        } catch (Exception e) {
            logger.error("删除课件失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }
}
