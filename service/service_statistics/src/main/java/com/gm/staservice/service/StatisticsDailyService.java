package com.gm.staservice.service;

import com.gm.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author gm
 * @since 2020-06-08
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    Integer getCount(String day);

    Map<String, Object> getChartInfo(String type, String begin, String end);
}
