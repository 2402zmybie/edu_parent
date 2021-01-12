package com.hr.eduservice.controller.front;

import com.hr.commonutils.R;
import com.hr.eduservice.entity.EduCourse;
import com.hr.eduservice.entity.EduTeacher;
import com.hr.eduservice.service.EduCourseService;
import com.hr.eduservice.service.EduTeacherService;
import com.hr.eduservice.service.IndexFrontService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Driver;
import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@Api("前端首页显示接口")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private IndexFrontService indexFrontService;

    @ApiOperation("查询前8条热门课程, 查询前4条名师")
    @GetMapping("/index")
    public R index() {
        List<EduCourse> eduCourseList =  indexFrontService.getCourse();
        List<EduTeacher> eduTeacherList =  indexFrontService.getTeacher();
        return R.ok().data("eduList", eduCourseList).data("teacherList", eduTeacherList);
    }
}
