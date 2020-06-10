package com.gm.staservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-usercenter",fallback = UCenterFeignClient.class)
public interface UCenterClient {

    //根据条件查询网站的注册人数
    @GetMapping("/ucenter/user/registCount/{day}")
    public Integer registCount(@PathVariable("day") String day);

}
