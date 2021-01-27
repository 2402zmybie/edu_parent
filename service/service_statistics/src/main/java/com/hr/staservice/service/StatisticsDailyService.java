package com.hr.staservice.service;

import com.hr.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-27
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void registerCount(String day);
}
