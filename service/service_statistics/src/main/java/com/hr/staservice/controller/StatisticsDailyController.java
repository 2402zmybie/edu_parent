package com.hr.staservice.controller;


import com.hr.commonutils.R;
import com.hr.staservice.service.StatisticsDailyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-27
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;


    @ApiOperation("统计某一天的注册人数,生成统计数据")
    @GetMapping("/registerCount/{day}")
    private R registerCount(@PathVariable("day") String day) {
        statisticsDailyService.registerCount(day);
        return R.ok();
    }

    @ApiOperation("图标显示,返回两部分数据, 日期json数组,数量json数组")
    @GetMapping("/showData/{type}/{begin}/{end}")
    private R showData(@PathVariable("type") String type,@PathVariable("begin") String begin,
                       @PathVariable("end") String end) {
        Map<String,Object> map = statisticsDailyService.showData(type,begin,end);
        return R.ok().data(map);
    }

}

