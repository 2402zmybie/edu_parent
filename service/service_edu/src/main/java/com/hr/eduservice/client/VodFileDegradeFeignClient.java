package com.hr.eduservice.client;

import com.hr.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient{

    //熔断机制, 出错之后会回调
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除单个视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("多个视频出错");
    }
}
