package com.hr.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hr.commonutils.R;
import com.hr.eduservice.entity.EduCourse;
import com.hr.eduservice.entity.EduTeacher;
import com.hr.eduservice.entity.vo.CourseFrontVo;
import com.hr.eduservice.service.EduCourseService;
import com.hr.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@Api("前台前端课程")
@CrossOrigin
public class CourseFrontController {


    @Autowired
    private EduCourseService eduCourseService;


    @ApiOperation("分页查询课程")
    @PostMapping("/getCourseFrontList/{page}/{limit}")
    public R getCourseFrontList(@PathVariable Integer page,
                                @PathVariable Integer limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo
                                ) {
        Page<EduCourse> eduCoursePage = new Page<>(page, limit);
        Map<String,Object> map =  eduCourseService.getCourseFrontList(eduCoursePage, courseFrontVo);
        return R.ok().data(map);
    }




}
