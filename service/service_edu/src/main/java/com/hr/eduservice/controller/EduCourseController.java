package com.hr.eduservice.controller;


import com.hr.commonutils.R;
import com.hr.eduservice.entity.vo.CourseInfoVo;
import com.hr.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.output.AppendableOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-04
 */
@Api("课程")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation("添加课程")
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String courseId =  eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", courseId);
    }

    @ApiOperation("根据课程查询课程基本信息")
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo =  eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    @ApiOperation("修改课程")
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }



}

