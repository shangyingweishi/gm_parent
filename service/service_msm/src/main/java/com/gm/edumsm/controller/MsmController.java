package com.gm.edumsm.controller;

import com.gm.commonutils.R;
import com.gm.edumsm.service.MsmService;
import com.gm.edumsm.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //发送短信方法
    @GetMapping("/send/{phone}")
    public R sendMsg(@PathVariable String phone) {

        //从redis中取值，如果有，则说明之前发送成功
        String code = redisTemplate.opsForValue().get("phone");
        if (!StringUtils.isEmpty(code)){
            return R.ok();
        }

        //如果redis中没有code，则随机生成code发送短信并存入redis，设置code过期事件为5分钟
        code = RandomUtils.getFourBitRandom();

        Map<String, Object> map = new HashMap<>();
        map.put("code", code);

        Boolean send = msmService.send(map, phone);

        if (send) {
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("验证码发送失败");
        }

    }
}
