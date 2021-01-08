package com.hr.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hr.eduservice.entity.EduChapter;
import com.hr.eduservice.entity.EduVideo;
import com.hr.eduservice.entity.chapter.ChapterVo;
import com.hr.eduservice.entity.chapter.VideoVo;
import com.hr.eduservice.mapper.EduChapterMapper;
import com.hr.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.eduservice.service.EduVideoService;
import com.hr.servicebase.exceptionhandler.EduException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-04
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //1 根据课程id查询里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2 根据课程id查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);

        //封装返回的集合
        List<ChapterVo> finalList = new ArrayList<>();
        //3 遍历查询章节list集合进行封装
        for (EduChapter eduChapter : eduChapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);

            //4 遍历小结list集合进行封装
            List<VideoVo> videoVoList = new ArrayList<>();
            for (EduVideo eduVideo : eduVideoList) {
                if(eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);

            finalList.add(chapterVo);
        }
        return finalList;
    }

    //删除章节, 如果章节下面有小节, 则不让其删除, 否则则能删除
    @Override
    public boolean deleteChapter(String id) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", id);
        //查出复合条件的 条数
        int count = eduVideoService.count(videoQueryWrapper);
        if(count > 0) {
            throw new EduException(20001, "章节下面有小节,不能删除");
        }else {
            int result = baseMapper.deleteById(id);
            return result > 0;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
