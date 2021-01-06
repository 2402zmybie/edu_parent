package com.hr.eduservice.controller;


import com.hr.commonutils.R;
import com.hr.eduservice.entity.EduVideo;
import com.hr.eduservice.service.EduVideoService;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-04
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    //TODO 删除小节的时候  视频也要删除
    @DeleteMapping("/deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id) {
        boolean flag = videoService.removeById(id);
        if(flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }

}

