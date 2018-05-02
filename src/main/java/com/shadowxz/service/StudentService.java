package com.shadowxz.service;

import java.util.List;

import com.shadowxz.domain.Student;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/4/23 21:37
 * @Modified by:
 */
public interface StudentService {

    String findPasswordByStudentId(String studentId);

    Student findStudentByStudentId(String studentId);

    void modifyStudentInfo(Student student);

    List<Student> findStudentByClass(String classId);

    void addStudent(Student student);

    List<String> findAllClass();

}
