package com.hr.oss.conrtoller;

import com.hr.commonutils.R;
import com.hr.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
@Slf4j
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/uploadOssFile")
    public R uploadOssFile(MultipartFile file) {
        //获取上传文件
        String url =  ossService.uploadFileAvatar(file);
        log.error(url);
        return R.ok().data("url", url);
    }
}
