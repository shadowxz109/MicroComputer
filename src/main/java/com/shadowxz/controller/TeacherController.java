package com.shadowxz.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shadowxz.domain.Constant;
import com.shadowxz.domain.Teacher;
import com.shadowxz.service.TeacherService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:43
 * @Modified by:
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    
    Logger logger = LoggerFactory.getLogger(TeacherController.class);
    
    @Autowired
    TeacherService teacherService;

    @Value("${upload.dir}")
    String uploadDir;

    @RequestMapping(value = "/session",method = RequestMethod.GET)
    public @ResponseBody
    Map<String,Object> cookieLoginIn(HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Cookie[] cookies = request.getCookies();
            String status = "";
            String loginTea = "";
            if(request.getSession().getAttribute("teacherId") == null) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie cookie = cookies[i];
                    if (cookie.getName().equals("status")) {
                        status = cookie.getValue();
                    } else if (cookie.getName().equals("loginASDGA")) {
                        loginTea = cookie.getValue();
                    }
                }
                if (status.equals(String.valueOf(Constant.LOGIN_CODE_SUCC))) {
                    request.getSession().setAttribute("teacherId",loginTea);
                }
            }
            result.put("msg_no",Constant.LOGIN_CODE_SUCC);
        } catch (Exception e) {
            logger.error("cookie登录出错",e);
            result.put("msg_no",Constant.LOGIN_CODE_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/session",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> loginIn(@RequestParam("teacherId") String teacherId, @RequestParam("password") String passwordReq,
                                                    HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            String password = teacherService.findPasswordByTeacherId(teacherId);
            if(password != null){
                if(password.equals(passwordReq)){
                    request.getSession().setAttribute("teacherId",teacherId);
                    Cookie teacherIdcoo = new Cookie("loginASDGA",teacherId);
                    teacherIdcoo.setMaxAge(60*60*24*7);
                    teacherIdcoo.setPath("/");
                    response.addCookie(teacherIdcoo);
                    Cookie status = new Cookie("status",String.valueOf(Constant.LOGIN_CODE_SUCC));
                    status.setMaxAge(60*60*24*7);
                    status.setPath("/");
                    response.addCookie(status);
                    Teacher teacher = teacherService.findTeacherById(teacherId);
                    result.put("teacher",teacher);
                    result.put("msg_no", Constant.LOGIN_CODE_SUCC);
                    result.put("msg","登录成功");
                }else{
                    result.put("msg_no",Constant.LOGIN_CODE_ERR);
                    result.put("msg","密码错误");
                }
            }else{
                result.put("msg_no",Constant.LOGIN_CODE_ERR);
                result.put("msg","该教师编号不存在");
            }
        } catch (Exception e) {
            logger.error("教师登录错误",e);
            result.put("msg_no",Constant.LOGIN_CODE_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/session",method = RequestMethod.DELETE)
    public @ResponseBody Map<String,Object> loginOut(@RequestParam("teacherId") String teacherId,HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            request.getSession().removeAttribute("teacherId");
            Cookie[] cookies = request.getCookies();
            for(int i = 0;i < cookies.length; i++){
                Cookie cookie = cookies[i];
                if(cookie.getName().equals("status")){
                    cookie.setValue(String.valueOf(Constant.LOGIN_CODE_NOLOG));
                    cookie.setMaxAge(0);
                }
            }
            result.put("msg_no",Constant.LOGIN_CODE_ERR);
        } catch (Exception e) {
            logger.error("教师注销错误",e);
            result.put("msg_no",Constant.LOGIN_CODE_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/picture/{picture:.+}",method = RequestMethod.GET)
    public ResponseEntity<byte[]> getStudentPic(@PathVariable("picture") String picture){
        try {
            String path = uploadDir + "/picture";
            File file = new File(path + File.separator + picture);
            HttpHeaders headers = new HttpHeaders();
            String downloadFielName = new String(picture.getBytes("UTF-8"),"iso-8859-1");
            headers.setContentDispositionFormData("attachment", downloadFielName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }
}
