package com.gm.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gm.staservice.client.UCenterClient;
import com.gm.staservice.entity.StatisticsDaily;
import com.gm.staservice.mapper.StatisticsDailyMapper;
import com.gm.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author gm
 * @since 2020-06-08
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Resource
    private UCenterClient uCenterClient;

    @Override
    public Integer getCount(String day) {
        //每次统计都先删除统计天数的数据再重新生成
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        Integer count = uCenterClient.registCount(day);
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setRegisterNum(count);
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100, 200));

        baseMapper.insert(statisticsDaily);

        return count;
    }

    @Override
    public Map<String, Object> getChartInfo(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select(type, "date_calculated");
        List<StatisticsDaily> dailyList = baseMapper.selectList(wrapper);

        List<Integer> listCount = new ArrayList<>();//存数量
        List<String> listDate = new ArrayList<>();//存日期

        for (StatisticsDaily daily : dailyList) {
            listDate.add(daily.getDateCalculated());
            switch (type) {
                case "login_num":
                    listCount.add(daily.getLoginNum());
                    break;
                case "register_num":
                    listCount.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    listCount.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    listCount.add(daily.getCourseNum());
                    break;
                default:
                    break;

            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("listCount",listCount);
        map.put("listDate",listDate);

        return map;
    }
}
