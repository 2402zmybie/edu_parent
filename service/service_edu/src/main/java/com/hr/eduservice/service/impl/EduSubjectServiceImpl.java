package com.hr.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hr.eduservice.entity.EduSubject;
import com.hr.eduservice.entity.excel.SubjectData;
import com.hr.eduservice.entity.subject.OneSubject;
import com.hr.eduservice.entity.subject.TwoSubject;
import com.hr.eduservice.listener.SubjectExcelListener;
import com.hr.eduservice.mapper.EduSubjectMapper;
import com.hr.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<OneSubject> getAllSubject() {
        //查询所有一级分类
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", "0");
        List<EduSubject> oneEduSubjectList = baseMapper.selectList(oneWrapper);
        //查询所有二级分类
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id", "0");
        List<EduSubject> twoEduSubjectList = baseMapper.selectList(twoWrapper);

        //封装集合
        List<OneSubject> oneSubjectList = new ArrayList<>();
        for (int i = 0; i < oneEduSubjectList.size(); i++) {
            EduSubject oneEduSubject = oneEduSubjectList.get(i);

            OneSubject oneSubject = new OneSubject();
            //copy属性get对象,set对象
            BeanUtils.copyProperties(oneEduSubject, oneSubject);
            //查询二级分类并封装
            List<TwoSubject> twoSubjectList = new ArrayList<>();
            for (int j = 0; j < twoEduSubjectList.size(); j++) {
                EduSubject twoEduSubject = twoEduSubjectList.get(j);
                if(oneEduSubject.getId().equals(twoEduSubject.getParentId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    //copy属性get对象,set对象
                    BeanUtils.copyProperties(twoEduSubject, twoSubject);
                    twoSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoSubjectList);
            oneSubjectList.add(oneSubject);
        }

        return oneSubjectList;
    }
}
