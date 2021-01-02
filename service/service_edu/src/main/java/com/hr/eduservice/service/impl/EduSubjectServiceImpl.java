package com.hr.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.hr.eduservice.entity.EduSubject;
import com.hr.eduservice.entity.excel.SubjectData;
import com.hr.eduservice.listener.SubjectExcelListener;
import com.hr.eduservice.mapper.EduSubjectMapper;
import com.hr.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-02
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void addSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
