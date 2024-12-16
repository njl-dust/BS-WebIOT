package com.example.mapper;


import com.example.entity.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CourseMapper {

    @Select("select * from course")
    List<Course> selectAll();

    @Select("select * from course where name like concat( '%' , #{name} , '%' )")
    List<Course> selectAllName( String name );

    @Select("select * from course where no like concat( '%' , #{name} , '%' )")
    List<Course> selectAllNo( String name );

    @Select("select * from course where teacher like concat( '%' , #{name} , '%' )")
    List<Course> selectAllTeacher( String name );

    @Insert("insert into course (name,no,term,times,teacher,position) values(#{name},#{no},#{term},#{times},#{teacher},#{position})")
    void insert(Course course);
}
