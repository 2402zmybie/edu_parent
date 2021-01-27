package com.hr.staservice.controller;


import com.hr.commonutils.R;
import com.hr.staservice.service.StatisticsDailyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}

