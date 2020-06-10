package com.gm.eduservice.springcloudclient;

import com.gm.commonutils.R;
import org.springframework.stereotype.Component;

@Component
public class OrderFeignClient implements OrderClient{
    @Override
    public Boolean checkPayStatus(String courseId, String userId) {
        return false;
    }
}
