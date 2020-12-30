package com.hr.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hr.commonutils.R;
import com.hr.eduservice.entity.EduTeacher;
import com.hr.eduservice.entity.vo.TeacherQuery;
import com.hr.eduservice.service.impl.EduTeacherServiceImpl;
import com.hr.servicebase.exceptionhandler.EduException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ApiOperation("分页查询讲师")
    @GetMapping("/pageTeacher/{current}/{limit}")
    private R pageListTeacher(@PathVariable Integer current,
                              @PathVariable Integer limit) {
        //分页插件
        Page<EduTeacher> page = new Page<>(current,limit);
        //调用方法实现分页
        //调用方法的时候 底层封装,把分页所有数据封装到page对象里面
        eduTeacherService.page(page,null);
        //总记录数
        long total = page.getTotal();
        List<EduTeacher> teachers = page.getRecords();
        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", teachers);
        return R.ok().data(map);
    }


    @ApiOperation("条件分页查询讲师")
    @PostMapping("/pageListTeacherCondition/{current}/{limit}")
    private R pageListTeacherCondition(@PathVariable Integer current,
                                       @PathVariable Integer limit,
                                       @RequestBody(required = false) TeacherQuery teacherQuery) {
        //分页插件
        Page<EduTeacher> page = new Page<>(current,limit);
        //调用方法实现分页
        //调用方法的时候 底层封装,把分页所有数据封装到page对象里面
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)) {
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)) {
            // >=
            queryWrapper.ge("gmt_create", begin);
        }
        if(!StringUtils.isEmpty(end)) {
            // <=
            queryWrapper.le("gmt_create", end);
        }

        eduTeacherService.page(page,queryWrapper);
        //总记录数
        long total = page.getTotal();
        List<EduTeacher> teachers = page.getRecords();
        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", teachers);
        return R.ok().data(map);
    }


    @ApiOperation("添加讲师")
    @PostMapping("/addTeacher")
    private R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean result = eduTeacherService.save(eduTeacher);
        return result? R.ok() : R.error();
    }


    @ApiOperation("查询讲师")
    @GetMapping("/getTeacher/{id}")
    private R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);

        try {
            int i = 1 / 0;
        }catch (Exception e) {
            throw new EduException(20001,"执行了自定义异常处理");
        }
        return R.ok().data("teacher", eduTeacher);
    }

    @ApiOperation("修改讲师")
    @PostMapping("/updateTeacher")
    private R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean result = eduTeacherService.updateById(eduTeacher);
        return result? R.ok() : R.error();
    }

}

