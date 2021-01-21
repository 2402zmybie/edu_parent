package com.hr.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hr.eduservice.entity.EduCourse;
import com.hr.eduservice.entity.vo.CoursePublishVo;
import com.hr.eduservice.entity.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-01-04
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getPublishCourseInfo(String courseId);


    CourseWebVo getFrontCourseInfo(String courseId);
}
