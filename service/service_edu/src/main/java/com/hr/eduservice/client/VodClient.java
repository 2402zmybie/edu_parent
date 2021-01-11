package com.hr.eduservice.client;

import com.hr.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
//调用端 使用要调用的服务的名字,  但前提是service-vod已经注册在注册中心了
@FeignClient("service-vod")
public interface VodClient {

    //定义调用方法的路径(完全路径), 直接复制接口即可
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    R removeAlyVideo(@PathVariable("id") String id);


    @DeleteMapping("/eduvod/video/deleteBatch")
    R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
