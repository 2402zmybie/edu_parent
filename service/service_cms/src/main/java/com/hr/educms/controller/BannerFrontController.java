package com.hr.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hr.commonutils.R;
import com.hr.educms.entity.CrmBanner;
import com.hr.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-12
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
@Api("前台Banner")
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("/getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> crmBannerList = crmBannerService.selectAllBanner();
        return R.ok().data("bannerList", crmBannerList);
    }
}

