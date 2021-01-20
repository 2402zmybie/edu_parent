package com.hr.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hr.commonutils.R;
import com.hr.eduservice.entity.EduTeacher;
import com.hr.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@Api("前台前端讲师")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;


    @ApiOperation("分页查询讲师")
    @GetMapping("/pageTeacher/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable Integer page, @PathVariable Integer limit) {
        Page<EduTeacher> eduTeacherPage = new Page<>(page, limit);
        Map<String,Object> map =  eduTeacherService.getTeacherFrontList(eduTeacherPage);
        return R.ok().data(map);
    }


}
