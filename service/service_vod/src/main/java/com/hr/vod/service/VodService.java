package com.hr.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    String uploadAlyVideo(MultipartFile file);
}
