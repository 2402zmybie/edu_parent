package com.hr.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hr.commonutils.R;
import com.hr.educms.entity.CrmBanner;
import com.hr.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-12
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
@Api("后台管理Banner")
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation("分页查询Banner")
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        crmBannerService.page(bannerPage, null);
        return R.ok().data("items", bannerPage.getRecords()).data("total", bannerPage.getTotal());
    }

    @ApiOperation("获取Banner")
    @GetMapping("/get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation("新增Banber")
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    @ApiOperation("修改Banner")
    @PutMapping("/updateBanner")
    public R updateById(@RequestBody CrmBanner banner) {
        crmBannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation("删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        crmBannerService.removeById(id);
        return R.ok();
    }
}

