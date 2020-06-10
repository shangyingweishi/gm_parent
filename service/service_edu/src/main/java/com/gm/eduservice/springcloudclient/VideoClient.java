package com.gm.eduservice.springcloudclient;

import com.gm.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-video", fallback = VideoFeignClient.class)//service_video配置文件中的服务名
public interface VideoClient {
    @DeleteMapping("/eduvideo/uploadvideo/remove/{videoId}")
    public R delVideo(@PathVariable("videoId") String videoId);

    @DeleteMapping("/eduvideo/uploadvideo/removeBatch")
    public R delBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
