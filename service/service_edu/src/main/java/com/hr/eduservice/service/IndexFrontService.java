package com.hr.eduservice.service;

import com.hr.eduservice.entity.EduCourse;
import com.hr.eduservice.entity.EduTeacher;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IndexFrontService {
    List<EduCourse> getCourse();

    List<EduTeacher> getTeacher();

}
