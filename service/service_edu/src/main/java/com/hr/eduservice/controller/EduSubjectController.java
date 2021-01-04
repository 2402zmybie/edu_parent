package com.hr.eduservice.controller;


import com.hr.commonutils.R;
import com.hr.eduservice.entity.EduSubject;
import com.hr.eduservice.entity.subject.OneSubject;
import com.hr.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-02
 */
@Api("课程管理")
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;


    @ApiOperation("通过excel添加文件进数据库")
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.addSubject(file,eduSubjectService);
        return R.ok();
    }

    @ApiOperation("课程分类列表(树形)")
    @GetMapping("/getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list =  eduSubjectService.getAllSubject();
        return R.ok().data("list", list);
    }

}

