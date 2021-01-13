package com.hr.msmservice.controller;

import com.hr.commonutils.R;
import com.hr.msmservice.service.MsmService;
import com.hr.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate redisTemplate;

    //发送短信的方法
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable("phone") String phone) {
        //1 从redis获取验证码, 如果获取到就直接返回

        //生成随机值, 传递阿里云发送
        String code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = msmService.send(param, phone);
        if(isSend) {
            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }

    }


}
