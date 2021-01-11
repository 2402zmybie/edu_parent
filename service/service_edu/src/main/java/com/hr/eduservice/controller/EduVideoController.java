package com.hr.eduservice.controller;


import com.hr.commonutils.R;
import com.hr.eduservice.client.VodClient;
import com.hr.eduservice.entity.EduVideo;
import com.hr.eduservice.service.EduVideoService;
import com.hr.servicebase.exceptionhandler.EduException;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    //注入vodClient
    @Autowired
    private VodClient vodClient;

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }


    @DeleteMapping("/deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id) {
        EduVideo eduVideo = videoService.getById(id);
        //得到视频id
        String videoSourceId = eduVideo.getVideoSourceId();
        //远程调用nacos微服务实现 删除阿里云视频的方法
        if(!StringUtils.isEmpty(videoSourceId)) {
            R result = vodClient.removeAlyVideo(videoSourceId);
            if(result.getCode() == 20001) {
                throw new EduException(20001,"删除视频失败, 熔断器执行..");
            }
        }
        boolean flag = videoService.removeById(id);
        if(flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }


}

