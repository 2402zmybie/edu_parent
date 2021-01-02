package com.hr.eduservice.service;

import com.hr.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-02
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程Excel文件
    void addSubject(MultipartFile file, EduSubjectService eduSubjectService);
}
