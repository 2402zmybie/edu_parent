package com.hr.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hr.eduservice.entity.EduCourse;
import com.hr.eduservice.entity.EduTeacher;
import com.hr.eduservice.service.EduCourseService;
import com.hr.eduservice.service.EduTeacherService;
import com.hr.eduservice.service.IndexFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexFrontServiceImpl implements IndexFrontService {

    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduTeacherService eduTeacherService;

    @Cacheable(value = "course", key = "'courseList'")
    @Override
    public List<EduCourse> getCourse() {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 8");
        List<EduCourse> eduCourseList = eduCourseService.list(queryWrapper);
        return eduCourseList;
    }

    @Cacheable(value = "teacher", key = "'teacherList'")
    @Override
    public List<EduTeacher> getTeacher() {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.last("limit 4");
        List<EduTeacher> eduTeacherList = eduTeacherService.list(queryWrapper);
        return eduTeacherList;
    }
}
