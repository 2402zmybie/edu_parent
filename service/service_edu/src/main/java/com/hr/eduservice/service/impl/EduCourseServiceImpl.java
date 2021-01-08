package com.hr.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hr.eduservice.entity.EduCourse;
import com.hr.eduservice.entity.EduCourseDescription;
import com.hr.eduservice.entity.vo.CourseInfoVo;
import com.hr.eduservice.entity.vo.CoursePublishVo;
import com.hr.eduservice.entity.vo.CourseQuery;
import com.hr.eduservice.mapper.EduCourseMapper;
import com.hr.eduservice.service.EduChapterService;
import com.hr.eduservice.service.EduCourseDescriptionService;
import com.hr.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.eduservice.service.EduVideoService;
import com.hr.servicebase.exceptionhandler.EduException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-04
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //课程描述service
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduVideoService eduVideoService;


//    @Autowired
//    private EduCourseMapper eduCourseMapper;

    //添加课程基本信息的方法
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        //1 向课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        //复制属性
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert <=0 ) {
            //添加失败
            throw new EduException(20001,"添加课程信息失败");
        }

        //2 课程和简介表是一对一的关系, 简介表生成的id要和课程表的id一致
        //获取添加之后课程id
        String cid = eduCourse.getId();


        //3 向课程简介表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        //设置描述id就是课程id
        eduCourseDescription.setId(cid);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);
        //返回新增课程id
        return cid;
    }



    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
//        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        //2 查询描述表
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());
        return courseInfoVo;

        //1 查询课程表
//        EduCourse eduCourse = baseMapper.selectById(courseId);
//        EduCourse eduCourse = eduCourseMapper.mySelectById(courseId);
//        CourseInfoVo courseInfoVo = new CourseInfoVo();
//        BeanUtils.copyProperties(eduCourse,courseInfoVo);
//        //2 查询描述表
//        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
//        courseInfoVo.setDescription(courseDescription.getDescription());
//        return courseInfoVo;
    }


    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0) {
            throw new EduException(20001, "修改课程信息失败");
        }
        //修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }


    @Override
    public CoursePublishVo getCoursePublish(String courseId) {
        //调用mapper, 自己写的sql语句
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }

    @Override
    public void pageQuery(Page<EduCourse> pagePram, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified");
        if(courseQuery == null) {
            //没有查询条件
           baseMapper.selectPage(pagePram, null);
           return;
        }

        String title = courseQuery.getTitle();
        if(!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }
        String teacherId = courseQuery.getTeacherId();
        if(!StringUtils.isEmpty(teacherId)) {
            queryWrapper.like("teacher_id", teacherId);
        }
        String subjectId = courseQuery.getSubjectId();
        if(!StringUtils.isEmpty(subjectId)) {
            queryWrapper.like("subject_id", subjectId);
        }
        String subjectParentId = courseQuery.getSubjectParentId();
        if(!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.like("subject_parent_id", subjectParentId);
        }
        baseMapper.selectPage(pagePram, queryWrapper);
    }


    @Override
    public void deleteCourse(String courseId) {
        //删除小节
        eduVideoService.removeVideoByCourseId(courseId);
        //删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        //删除描述
        eduCourseDescriptionService.removeById(courseId);
        //删除课程
        int result = baseMapper.deleteById(courseId);
        if(result == 0) {
            throw new EduException(20001, "删除失败");
        }
    }
}
