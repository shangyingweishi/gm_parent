package com.gm.staservice.client;

import org.springframework.stereotype.Component;

@Component
public class UCenterFeignClient implements UCenterClient {
    @Override
    public Integer registCount(String day) {
        return -1;
    }
}
