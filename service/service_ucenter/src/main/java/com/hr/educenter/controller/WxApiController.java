package com.hr.educenter.controller;


import com.google.gson.Gson;
import com.hr.educenter.entity.UcenterMember;
import com.hr.educenter.service.UcenterMemberService;
import com.hr.educenter.utils.ConstantWxUtils;
import com.hr.educenter.utils.HttpClientUtils;
import com.hr.servicebase.exceptionhandler.EduException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
@Slf4j
public class WxApiController {

    @Autowired
    private ConstantWxUtils constantWxUtils;

    @Autowired
    private UcenterMemberService ucenterMemberService;

    //微信登录扫描调用的接口
    @GetMapping("/callback")
    public String callback(String code, String state) {
        try {
            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    constantWxUtils.getApp_id(),
                    constantWxUtils.getApp_secret(),
                    code);
            //使用httpclient调用, 获取access_token和openid
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenInfo: " + accessTokenInfo);
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String accessToken = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");
            //访问微信的资源服务器, 获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String userInfo = HttpClientUtils.get(userInfoUrl);
            //{"openid":"o3_SC50t9tQZRgN0dq3MtTXffxyg","nickname":"Gatsby","sex":1,"language":"zh_CN","city":"","province":"Shanghai","country":"CN","headimgurl":"https:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/11gKZk9TQkfXibsqPUeg8muTyosaQaSmicMkxeGg9PTwSxiaA4XvnDj5RuHMhrwHwvl0aWEN5yAgShDxQ59TQTF2g\/132","privilege":[],"unionid":"oWgGz1HTPeZZCw9n7D9Ib7XCJBtg"}
            log.error("userInfo:" + userInfo);
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String) userInfoMap.get("nickname");
            String headimgurl = (String) userInfoMap.get("headimgurl");
            //存库, 先查询是否有这个微信登录的用户
            UcenterMember ucenterMember =  ucenterMemberService.getOpenIdMember(openid);
            if(ucenterMember == null) {
                //初次微信登录
                ucenterMember = new UcenterMember();
                ucenterMember.setOpenid(openid);
                ucenterMember.setNickname(nickname);
                ucenterMember.setAvatar(headimgurl);
                ucenterMemberService.save(ucenterMember);
            }

            //返回首页面
            return "redirect:http://localhost:3000";

        } catch (Exception e) {
            throw new EduException(20001,"微信登录失败");
        }

    }


    @GetMapping("login")
    public String genQrConnect(HttpSession session) {

        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        String redirectUrl = constantWxUtils.getRedirect_url();//获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new EduException(20001, e.getMessage());
        }

        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        System.out.println("state = " + state);

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                constantWxUtils.getApp_id(),
                redirectUrl,
                state);

        return "redirect:" + qrcodeUrl;
    }
}
