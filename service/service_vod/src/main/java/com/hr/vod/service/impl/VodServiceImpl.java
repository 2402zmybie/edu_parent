package com.hr.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.hr.commonutils.R;
import com.hr.servicebase.exceptionhandler.EduException;
import com.hr.vod.service.VodService;
import com.hr.vod.utils.ConstantVodProperties;
import com.hr.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    @Autowired
    private ConstantVodProperties constantVodProperties;


    @Override
    public String uploadAlyVideo(MultipartFile file) {
        //使用阿里云视频点播上传文件
        String fileName = file.getOriginalFilename();
        String title = fileName.substring(0, fileName.lastIndexOf("."));
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(constantVodProperties.getKeyid(), constantVodProperties.getKeysecret(), title,fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = null;
            if(response.isSuccess()) {
                videoId = response.getVideoId();
            }else {
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public void removeMoreAlyVideo(List<String> videoIdList) {
        //初始化对象
        DefaultAcsClient client = InitVodClient.initVodClient(constantVodProperties.getKeyid(), constantVodProperties.getKeysecret());
        //创建删除视频的request对象
        DeleteVideoRequest request = new DeleteVideoRequest();
        String idStrs = StringUtils.join(videoIdList.toArray(), ",");
        //批量删除视频文件
        request.setVideoIds(idStrs);
        try {
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new EduException(20001, "删除视频失败");
        }
    }
}
