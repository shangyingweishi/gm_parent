package com.gm.staservice.controller;


import com.gm.commonutils.R;
import com.gm.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author gm
 * @since 2020-06-08
 */
@RestController
@RequestMapping("/staservice/statistics")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //统计某一天的数据
    @PostMapping("/getCountRegist/{day}")
    public R getCountRegist(@PathVariable String day){

        Integer count = statisticsDailyService.getCount(day);

        return R.ok().data("count", count);
    }

    //根据数据类型和时间段显示数据
    @GetMapping("/getChartInfo/{type}/{begin}/{end}")
    public R getChartInfo(@PathVariable String type,@PathVariable String begin,@PathVariable String end){

        Map<String, Object> map = statisticsDailyService.getChartInfo(type, begin, end);

        return R.ok().data(map);

    }

}

