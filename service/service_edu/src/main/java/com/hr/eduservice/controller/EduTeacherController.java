package com.hr.eduservice.controller;


import com.hr.commonutils.R;
import com.hr.eduservice.entity.EduTeacher;
import com.hr.eduservice.service.impl.EduTeacherServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-29
 */
@Api("讲师管理")
@RestController
@CrossOrigin //跨域
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherServiceImpl eduTeacherService;

    @ApiOperation("所有讲师列表")
    @GetMapping("/findAll")
    private R findAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/{id}")
    private R removeTeacher(
                            @ApiParam(name = "id", value = "讲师ID", required = true)
                            @PathVariable("id") String id) {
        boolean result = eduTeacherService.removeById(id);
        return result ? R.ok() : R.error();
    }


}

