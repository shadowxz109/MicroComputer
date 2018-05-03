package com.shadowxz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.shadowxz.domain.Student;

@Component
@Mapper
public interface StudentMapper {
    int deleteByPrimaryKey(String studentId);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(String studentId);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    String selectPasswordByStudentId(String studentId);

    List<Student> selectStudentByClazzs(List<String> clazzList);

    List<String> selectStudentIdByClazzs(List<String> clazzList);

    List<String> selectAllClass();

}