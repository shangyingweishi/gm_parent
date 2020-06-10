package com.gm.eduservice.springcloudclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-order", fallback = OrderFeignClient.class)
public interface OrderClient {
    //根据课程id和登录用户判断用户是否付款完成
    @GetMapping("/eduorder/order/checkPayStatus/{courseId}/{userId}")
    public Boolean checkPayStatus(@PathVariable("courseId") String courseId, @PathVariable("userId") String userId);
}
