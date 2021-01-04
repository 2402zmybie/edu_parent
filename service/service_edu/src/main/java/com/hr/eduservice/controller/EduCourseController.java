package com.hr.eduservice.controller;


import com.hr.commonutils.R;
import com.hr.eduservice.entity.vo.CourseInfoVo;
import com.hr.eduservice.service.EduCourseService;
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
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String courseId =  eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", courseId);
    }

}

