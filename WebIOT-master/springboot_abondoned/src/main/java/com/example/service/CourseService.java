package com.example.service;

import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseService {

    @Resource
    private CourseMapper courseMapper;

    //查询
    public PageInfo<Course> selectPage(Integer pageNumber, Integer pageSize, String name, String value) {
        PageHelper.startPage(pageNumber, pageSize);

        List<Course> courseList;

        if (value.equals("课程名称")) {
            courseList = courseMapper.selectAllName(name);
        }else if (value.equals("课程编号")) {
            courseList = courseMapper.selectAllNo(name);
        }else{
            courseList = courseMapper.selectAllTeacher(name);
        }

        return PageInfo.of(courseList);
    }

    //新增
    public void add(Course course) {
        courseMapper.insert(course);
    }
}
