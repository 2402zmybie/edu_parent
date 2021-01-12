package com.hr.educms.service.impl;

import com.hr.educms.entity.CrmBanner;
import com.hr.educms.mapper.CrmBannerMapper;
import com.hr.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-12
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public List<CrmBanner> selectAllBanner() {
        List<CrmBanner> crmBannerList = baseMapper.selectList(null);
        return crmBannerList;
    }
}
