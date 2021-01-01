package com.hr.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.hr.oss.service.OssService;
import com.hr.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private ConstantPropertiesUtils constantPropertiesUtils;


    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = constantPropertiesUtils.getEndpoint();
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = constantPropertiesUtils.getKeyid();
        String accessKeySecret = constantPropertiesUtils.getKeysecret();
        String bucketName = constantPropertiesUtils.getBucketname();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String uuid = UUID.randomUUID().toString();
        String fileName = file.getOriginalFilename();
        //1 避免上传同一个文件,文件名称一样
        fileName = uuid + fileName;
        //2 把文件按照日期进行分类
        //获取当前日期
        String datePath = new DateTime().toString("yyyy/MM/dd");
        //2021/1/1/ew-wedfsa01.jpg
        fileName = datePath + "/" + fileName;
        ossClient.putObject(bucketName, fileName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        //获取url地址
        String uploadUrl = "http://" + bucketName + "." + endpoint + "/" + fileName;
        return uploadUrl;
    }
}
