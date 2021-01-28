package com.hr.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.commonutils.R;
import com.hr.staservice.client.UcenterClient;
import com.hr.staservice.entity.StatisticsDaily;
import com.hr.staservice.mapper.StatisticsDailyMapper;
import com.hr.staservice.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-27
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {
        //添加记录之前 先删除表相同日期的数据
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated",day);
        baseMapper.delete(queryWrapper);

        R registerR = ucenterClient.countRegister(day);
        Integer registerNum = (Integer) registerR.getData().get("countRegister");
        //把获取到的注册人数 添加到统计表中
        Integer loginNum = RandomUtils.nextInt(100, 200);
        Integer videoViewNum = RandomUtils.nextInt(100, 200);
        Integer courseNum = RandomUtils.nextInt(100, 200);

        //创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);
        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> showData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.between("date_calculated", begin, end);
        List<StatisticsDaily> dayList = baseMapper.selectList(dayQueryWrapper);

        Map<String, Object> map = new HashMap<>();
        List<Integer> dataList = new ArrayList<Integer>();
        List<String> dateList = new ArrayList<String>();

        for (int i = 0; i < dayList.size(); i++) {
            StatisticsDaily daily = dayList.get(i);
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        map.put("dataList", dataList);
        map.put("dateList", dateList);

        return map;

    }
}
