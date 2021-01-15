package com.hr.educenter.controller;


import com.hr.commonutils.JwtUtils;
import com.hr.commonutils.R;
import com.hr.educenter.entity.UcenterMember;
import com.hr.educenter.entity.vo.RegisterVo;
import com.hr.educenter.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @ApiOperation("注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo) {
        ucenterMemberService.register(registerVo);
        return R.ok();
    }


    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest httpServletRequest) {
        //根据jwt工具类的方法, 根据request对象获取头信息, 返回用户id
        String id = JwtUtils.getMemberIdByJwtToken(httpServletRequest);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(id);
        return R.ok().data("userInfo", member);
    }
}

