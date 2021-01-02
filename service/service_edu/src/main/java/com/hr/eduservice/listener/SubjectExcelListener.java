package com.hr.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hr.eduservice.entity.EduSubject;
import com.hr.eduservice.entity.excel.SubjectData;
import com.hr.eduservice.service.EduSubjectService;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private final EduSubjectService eduSubjectService;

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        //一行一行读, 每次读取判断两个值, 第一个值一级分类, 第二个值 二级分类
        //查询是否已经有一级分类了
        EduSubject existOneSubject = this.existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        if(existOneSubject == null) {
            //添加一级分类
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(existOneSubject);
        }

        //获取一级分类的id值
        String pid = existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService, subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject == null) {
            //添加二级分类
            existOneSubject = new EduSubject();
            existOneSubject.setParentId(pid);
            existOneSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(existOneSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    private EduSubject existOneSubject(EduSubjectService eduSubjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject;
    }

    private EduSubject existTwoSubject(EduSubjectService eduSubjectService, String name,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject;
    }
}
