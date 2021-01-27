package com.hr.educenter.service;

import com.hr.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-14
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer countRegister(String day);
}
