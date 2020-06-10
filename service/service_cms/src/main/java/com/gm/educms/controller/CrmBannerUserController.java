package com.gm.educms.controller;


import com.gm.commonutils.R;
import com.gm.educms.entity.CrmBanner;
import com.gm.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author gm
 * @since 2020-06-02
 */
@RestController
@RequestMapping("/educms/userbanner")
//@CrossOrigin
@Api(description = "页面banner数据展示")
public class CrmBannerUserController {

    @Autowired
    private CrmBannerService bannerService;

    @Cacheable(key = "'bannerList'", value = "banner")//向redis里面缓存数据
    @GetMapping("/getAllBanner")
    public R getAllBanner(){

        List<CrmBanner> crmBanners = bannerService.list(null);

        return R.ok().data("crmBanners", crmBanners);

    }

}

