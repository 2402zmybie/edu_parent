package com.hr.msmservice.controller;

import com.hr.commonutils.R;
import com.hr.msmservice.service.MsmService;
import com.hr.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        String code  = (String) redisTemplate.boundValueOps(phone).get();
        if(!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        //2 如果redis获取不到,进行阿里云发送
        //生成随机值, 传递阿里云发送
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = msmService.send(param, phone);
        if(isSend) {
            //发送成功,把发送成功的验证码放到redis里面
            //设置有效时间 为365天
            redisTemplate.opsForValue().set(phone, code,365, TimeUnit.DAYS);

            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }

    }


}
