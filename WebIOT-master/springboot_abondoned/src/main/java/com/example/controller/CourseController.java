package com.example.controller;

import com.example.common.Result;
import com.example.entity.Course;
import com.example.service.CourseService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1")Integer pageNumber,
                             @RequestParam(defaultValue = "1")Integer pageSize,
                             @RequestParam(defaultValue = "") String name,
                             @RequestParam(defaultValue = "课程名称")String value){
        PageInfo<Course> pageInfo = courseService.selectPage(pageNumber, pageSize, name, value);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result insertCourse(@RequestBody Course course){
        courseService.add(course);
        return Result.success();
    }


}
