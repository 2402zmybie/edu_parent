package com.hr.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.hr.commonutils.R;
import com.hr.servicebase.exceptionhandler.EduException;
import com.hr.vod.service.VodService;
import com.hr.vod.utils.ConstantVodProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.hr.vod.utils.InitVodClient.initVodClient;

@RestController
@RequestMapping("/eduvod/video")
@Api("视频点播")
@CrossOrigin
public class VodController {

    @Autowired
    private ConstantVodProperties constantVodProperties;

    @Autowired
    private VodService vodService;

    @ApiOperation("视频点播上传")
    @PostMapping("/uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file) {
        String videoId = vodService.uploadAlyVideo(file);
        return R.ok().data("videoId", videoId);
    }

    @ApiOperation("根据视频id删除阿里云视频")
    @DeleteMapping("/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id) {
        //初始化对象
        DefaultAcsClient client = initVodClient(constantVodProperties.getKeyid(), constantVodProperties.getKeysecret());
        //创建删除视频的request对象
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(id);
        try {
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new EduException(20001, "删除视频失败");
        }

    }

    //删除多个阿里云视频的方法
    @ApiOperation("删除多个阿里云视频的方法")
    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }


    @ApiOperation("根据视频id获取视频凭证")
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        DefaultAcsClient client = initVodClient(constantVodProperties.getKeyid(), constantVodProperties.getKeysecret());
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            response = client.getAcsResponse(request);
            //播放凭证
            String playAuth= response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new EduException(20001,"获取视频凭证失败");
        }
    }

}

