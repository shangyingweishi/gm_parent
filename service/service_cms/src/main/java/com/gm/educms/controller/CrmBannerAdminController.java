package com.gm.educms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gm.commonutils.R;
import com.gm.educms.entity.CrmBanner;
import com.gm.educms.entity.vo.BannerInfoVo;
import com.gm.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/educms/adminbanner")
//@CrossOrigin
@Api(description = "后台banner管理")
public class CrmBannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @PostMapping("/getBannerCondition/{current}/{limit}")
    public R getBannerCondition(@PathVariable int current, @PathVariable int limit, @RequestBody BannerInfoVo bannerInfoVo){

        IPage<CrmBanner> bannerCondition = bannerService.getBannerCondition(current, limit, bannerInfoVo);

        return R.ok().data("lists", bannerCondition.getRecords()).data("total", bannerCondition.getTotal());
    }

    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){

        boolean save = bannerService.save(crmBanner);

        if (save){
            return R.ok();
        }else {
            return R.error();
        }

    }

    @DeleteMapping("/delBanner/{id}")
    public R delBanner(@PathVariable String id){

        boolean b = bannerService.removeById(id);

        if (b){
            return R.ok();
        }else {
            return R.error();
        }

    }


    @PostMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        boolean b = bannerService.updateById(crmBanner);

        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("/getBanner/{id}")
    public R getBannerByid(@PathVariable String id){

        CrmBanner crmBanner = bannerService.getById(id);

        return R.ok().data("banner", crmBanner);

    }

}

