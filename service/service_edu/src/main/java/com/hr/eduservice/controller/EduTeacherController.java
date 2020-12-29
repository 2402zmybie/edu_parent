package com.hr.eduservice.controller;


import com.hr.eduservice.entity.EduTeacher;
import com.hr.eduservice.service.impl.EduTeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-29
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherServiceImpl eduTeacherService;

    @GetMapping("/findAll")
    private List<EduTeacher> findAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return list;
    }

}

