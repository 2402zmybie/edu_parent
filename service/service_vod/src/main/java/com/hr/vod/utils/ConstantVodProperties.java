package com.hr.vod.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aliyun.vod.file")
@Data
public class ConstantVodProperties {

    private String keyid;
    private String keysecret;
}
