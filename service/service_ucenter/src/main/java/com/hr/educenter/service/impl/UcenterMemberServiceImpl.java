package com.hr.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hr.commonutils.JwtUtils;
import com.hr.commonutils.MD5;
import com.hr.educenter.entity.UcenterMember;
import com.hr.educenter.mapper.UcenterMemberMapper;
import com.hr.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.servicebase.exceptionhandler.EduException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-14
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Override
    public String login(UcenterMember ucenterMember) {
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new EduException(20001,"登录失败");
        }
        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember member = baseMapper.selectOne(wrapper);
        if(member == null) {
            throw new EduException(20001,"登录失败");
        }
        //判断密码
        if(!MD5.encrypt(password).equals(member.getPassword())) {
            throw new EduException(20001,"登录失败");
        }

        //判断用户是否禁用
        if(member.getIsDisabled()) {
            //账户禁用
            throw new EduException(20001,"登录失败");
        }

        //登录成功, 生成token字符串
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }
}
