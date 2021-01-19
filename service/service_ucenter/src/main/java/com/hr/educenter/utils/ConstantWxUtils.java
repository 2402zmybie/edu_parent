package com.hr.educenter.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wx.open")
@Data
public class ConstantWxUtils {

    private String app_id;
    private String app_secret;
    private String redirect_url;
}
