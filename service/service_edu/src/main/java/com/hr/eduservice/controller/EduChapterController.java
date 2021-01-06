package com.hr.eduservice.controller;


import com.hr.commonutils.R;
import com.hr.eduservice.entity.EduChapter;
import com.hr.eduservice.entity.chapter.ChapterVo;
import com.hr.eduservice.entity.subject.OneSubject;
import com.hr.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-04
 */
@Api("课程大纲(包含章节和小结)")
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation("课程大纲(树形)")
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list =  eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }


    @ApiOperation("添加章节")
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    @ApiOperation("根据章节id查询")
    @GetMapping("/getChapterInfo/{id}")
    public R getChapterInfo(@PathVariable String id) {
        EduChapter eduChapter = eduChapterService.getById(id);
        return R.ok().data("chapter", eduChapter);
    }

    @ApiOperation("修改章节")
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    @ApiOperation("删除章节")
    @DeleteMapping("/deleteChapter/{id}")
    public R deleteChapter(@PathVariable String id) {
       boolean flag = eduChapterService.deleteChapter(id);
       if(flag) {
           return R.ok();
       }else {
           return R.error();
       }
    }

}

