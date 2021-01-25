package com.hr.eduorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.commonutils.ordervo.CourseWebVoOrder;
import com.hr.commonutils.ordervo.UcenterMemberOrder;
import com.hr.eduorder.client.EduClient;
import com.hr.eduorder.client.UCenterClient;
import com.hr.eduorder.entity.Order;
import com.hr.eduorder.mapper.OrderMapper;
import com.hr.eduorder.service.OrderService;
import com.hr.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UCenterClient uCenterClient;

    @Override
    public String createOrder(String courseId, String memberIdByJwtToken) {
        //通过远程调用获取用户信息
        UcenterMemberOrder ucenterMemberOrder = uCenterClient.getUserInfoOrder(memberIdByJwtToken);
        //通过远程调用获取课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(ucenterMemberOrder.getMobile());
        order.setNickname(ucenterMemberOrder.getNickname());
        //支付状态 0 未支付  1 已支付
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
