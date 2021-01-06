package com.hr.eduservice.service;

import com.hr.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-04
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String id);
}
