package com.gm.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gm.educms.entity.CrmBanner;
import com.gm.educms.entity.vo.BannerInfoVo;
import com.gm.educms.mapper.CrmBannerMapper;
import com.gm.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author gm
 * @since 2020-06-02
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public IPage<CrmBanner> getBannerCondition(int current, int limit, BannerInfoVo bannerInfoVo) {

        Page<CrmBanner> page = new Page<>(current, limit);

        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(bannerInfoVo.getTitle())){
            wrapper.like("title", bannerInfoVo.getTitle());
        }
        if (!StringUtils.isEmpty(bannerInfoVo.getBegin())){
            wrapper.ge("gmt_Create", bannerInfoVo.getBegin());
        }

        wrapper.orderByDesc("gmt_Create");

        IPage<CrmBanner> crmBannerIPage = baseMapper.selectPage(page, wrapper);

        return crmBannerIPage;
    }
}
