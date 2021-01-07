package com.hr.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hr.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.eduservice.entity.vo.CourseInfoVo;
import com.hr.eduservice.entity.vo.CoursePublishVo;
import com.hr.eduservice.entity.vo.CourseQuery;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-04
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublish(String courseId);

    void pageQuery(Page<EduCourse> pagePram, CourseQuery courseQuery);
}
