package com.hr.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.hr.commonutils.R;
import com.hr.servicebase.exceptionhandler.EduException;
import com.hr.vod.service.VodService;
import com.hr.vod.utils.ConstantVodProperties;
import com.hr.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
        DefaultAcsClient client = InitVodClient.initVodClient(constantVodProperties.getKeyid(), constantVodProperties.getKeysecret());
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

}

