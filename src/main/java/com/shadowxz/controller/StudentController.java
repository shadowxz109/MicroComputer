package com.shadowxz.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;

import com.shadowxz.domain.Constant;
import com.shadowxz.domain.Student;
import com.shadowxz.service.StudentService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:43
 * @Modified by:
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/excel",method = RequestMethod.GET)
    public ResponseEntity<byte[]> getExcelTemplate(HttpServletRequest request){
        try {
            String filename = "学生信息模版";
            String path = request.getServletContext().getRealPath("/excel/");
            File file = new File(path + File.separator + filename);
            HttpHeaders headers = new HttpHeaders();
            String downloadFielName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
            headers.setContentDispositionFormData("attachment", downloadFielName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }

    @RequestMapping(value = "/excel",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> addStudentFromExcel(MultipartFile excel,HttpServletRequest request){
        //todo
        return null;
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> addStudent(Student student, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            if(request.getSession().getAttribute("teacherId") != null) {
                studentService.addStudent(student);
                result.put("msg_no", Constant.GET_DATA_SUCC);
            }
        } catch (Exception e) {
            logger.error("新增学生信息失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/class/{classId}",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getStudentList(@PathVariable("classId") String classId){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            List<String> clazz = new ArrayList<>(1);
            clazz.add(classId);
            List<Student> list = studentService.findStudentByClass(clazz);
            result.put("msg_no", Constant.GET_DATA_SUCC);
            result.put("data", list);
        } catch (Exception e) {
            logger.error("查询班级学生信息失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/{urlStudentId}",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getStudentInfo(@PathVariable("urlStudentId") String urlStudentId){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Student student = studentService.findStudentByStudentId(urlStudentId);
            result.put("msg_no",Constant.GET_DATA_SUCC);
            result.put("data",student);
        } catch (Exception e) {
            logger.error("查询学生信息失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/{urlStudentId}",method = RequestMethod.PUT)
    public @ResponseBody Map<String,Object> modifyStudetInfo(@PathVariable("urlStudentId") String urlStudentId,HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Object studentId = request.getSession().getAttribute("studentId");
            if(studentId != null && studentId.toString().equals(urlStudentId)){
                Student student = studentService.findStudentByStudentId(urlStudentId);
                if(student != null){
                    studentService.modifyStudentInfo(student);
                    result.put("msg_no",Constant.GET_DATA_SUCC);
                }
            }
        } catch (Exception e) {
            logger.error("修改学生信息失败",e);
            result.put("msg_no",Constant.GET_DATA_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/session",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> cookieLoginIn(HttpServletRequest request){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            Cookie[] cookies = request.getCookies();
            String status = "";
            String loginStu = "";
            if(request.getSession().getAttribute("studentId") == null) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie cookie = cookies[i];
                    if (cookie.getName().equals("status")) {
                        status = cookie.getValue();
                    } else if (cookie.getName().equals("loginStu")) {
                        loginStu = cookie.getValue();
                    }
                }
                if (status.equals(String.valueOf(Constant.LOGIN_CODE_SUCC))) {
                    request.getSession().setAttribute("studentId",loginStu);
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
    public @ResponseBody Map<String,Object> loginIn(@RequestParam("studentId") String studentId, @RequestParam("password") String passwordReq,
                                                   HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
             String password = studentService.findPasswordByStudentId(studentId);
            if(password != null){
                if(password.equals(passwordReq)){
                    request.getSession().setAttribute("studentId",studentId);
                    Cookie studentIdcoo = new Cookie("LoginStu",studentId);
                    studentIdcoo.setMaxAge(60*60*24*7);
                    studentIdcoo.setPath("/");
                    response.addCookie(studentIdcoo);
                    Cookie status = new Cookie("status",String.valueOf(Constant.LOGIN_CODE_SUCC));
                    status.setMaxAge(60*60*24*7);
                    status.setPath("/");
                    response.addCookie(status);
                    result.put("msg_no", Constant.LOGIN_CODE_SUCC);
                    result.put("msg","登录成功");
                }else{
                    result.put("msg_no",Constant.LOGIN_CODE_ERR);
                    result.put("msg","密码错误");
                }
            }else{
                result.put("msg_no",Constant.LOGIN_CODE_ERR);
                result.put("msg","该学号不存在");
            }
        } catch (Exception e) {
            logger.error("学生登录错误",e);
            result.put("msg_no",Constant.LOGIN_CODE_ERR);
        }
        return result;
    }

    @RequestMapping(value = "/session",method = RequestMethod.DELETE)
    public @ResponseBody Map<String,Object> loginOut(@RequestParam("studentId") String studentId,HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> result = new HashMap<>(Constant.RESULT_MAP_LENGTH);
        try {
            request.getSession().removeAttribute("studentId");
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
            logger.error("学生注销错误",e);
            result.put("msg_no",Constant.LOGIN_CODE_ERR);
        }
        return result;
    }
}
