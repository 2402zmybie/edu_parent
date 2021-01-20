package com.hr.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hr.commonutils.R;
import com.hr.eduservice.entity.EduCourse;
import com.hr.eduservice.entity.EduTeacher;
import com.hr.eduservice.service.EduCourseService;
import com.hr.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@Api("前台前端讲师")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;


    @ApiOperation("分页查询讲师")
    @GetMapping("/pageTeacher/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable Integer page, @PathVariable Integer limit) {
        Page<EduTeacher> eduTeacherPage = new Page<>(page, limit);
        Map<String,Object> map =  eduTeacherService.getTeacherFrontList(eduTeacherPage);
        return R.ok().data(map);
    }


    @ApiOperation("讲师详情")
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        //查询讲师基本信息
        EduTeacher teacher = eduTeacherService.getById(teacherId);
        //查询讲师所讲的课程
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = eduCourseService.list(queryWrapper);
        return R.ok().data("teacher",teacher).data("courseList", courseList);
    }


}
