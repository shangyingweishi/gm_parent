package com.gm.educms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gm.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gm.educms.entity.vo.BannerInfoVo;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author gm
 * @since 2020-06-02
 */
public interface CrmBannerService extends IService<CrmBanner> {

    IPage<CrmBanner> getBannerCondition(int current, int limit, BannerInfoVo bannerInfoVo);
}
