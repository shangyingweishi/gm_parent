package com.gm.eduorder.client;

import com.gm.eduorder.entity.vo.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-usercenter")
public interface UserClient {

    @GetMapping("/ucenter/user/getUserInfo/{id}")
    public UcenterMember getUserInfo(@PathVariable("id") String id);

}
