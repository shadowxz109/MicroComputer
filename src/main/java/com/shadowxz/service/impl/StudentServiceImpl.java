package com.shadowxz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadowxz.dao.StudentMapper;
import com.shadowxz.domain.Student;
import com.shadowxz.service.StudentService;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:40
 * @Modified by:
 */

@Service
public class StudentServiceImpl implements StudentService{

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    StudentMapper studentMapper;

    @Override
    public String findPasswordByStudentId(String studentId) {
        String result = null;
        try {
            result = studentMapper.selectPasswordByStudentId(studentId);
        } catch (Exception e) {
            logger.error("根据学号查询密码失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result ;
    }


    @Override
    public Student findStudentByStudentId(String studentId) {
        Student student = null;
        try {
            student = studentMapper.selectByPrimaryKey(studentId);
        } catch (Exception e) {
            logger.error("根据学号查询学生失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return student;
    }

    @Override
    public void modifyStudentInfo(Student student) {
        try {
            studentMapper.updateByPrimaryKeySelective(student);
        } catch (Exception e) {
            logger.error("根据学号查询学生失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> findStudentByClass(List<String> clazzList) {
        List<Student> list = null;
        try {
            list = studentMapper.selectStudentByClazzs(clazzList);
        } catch (Exception e) {
            logger.error("根据班级查询学生失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<String> findStudentIdByClass(List<String> clazzList) {
        List<String> list = null;
        try {
            list = studentMapper.selectStudentIdByClazzs(clazzList);
        } catch (Exception e) {
            logger.error("根据班级查询学号失败------------------->");
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void addStudent(Student student) {
        try {
            studentMapper.insertSelective(student);
        } catch (Exception e) {
            logger.error("添加学生信息失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<String> findAllClass() {
        List<String> list = null;
        try {
            list = studentMapper.selectAllClass();
        } catch (Exception e) {
            logger.error("查询所有班级失败------------------->");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;
    }
}
