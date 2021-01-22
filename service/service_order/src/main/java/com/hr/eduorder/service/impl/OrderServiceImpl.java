package com.hr.eduorder.service.impl;

import com.hr.eduorder.entity.Order;
import com.hr.eduorder.mapper.OrderMapper;
import com.hr.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-22
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public String createOrder(String courseId, String memberIdByJwtToken) {
        //通过远程调用获取用户信息

        //通过远程调用获取课程信息
        return null;
    }
}
