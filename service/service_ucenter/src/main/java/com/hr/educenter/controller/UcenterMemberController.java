package com.hr.educenter.controller;


import com.hr.commonutils.R;
import com.hr.educenter.entity.UcenterMember;
import com.hr.educenter.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-14
 */
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public R login(@RequestBody UcenterMember ucenterMember) {
        //返回token值 用于登录
        String token = ucenterMemberService.login(ucenterMember);
        return R.ok().data("token",token);
    }
}

