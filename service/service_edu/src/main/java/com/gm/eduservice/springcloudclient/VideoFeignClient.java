package com.gm.eduservice.springcloudclient;

import com.gm.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

//VideoClient的实现类，方法出问题才会执行下列方法，由熔断器hytrix操作
@Component
public class VideoFeignClient implements VideoClient {
    @Override
    public R delVideo(String videoId) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R delBatch(List<String> videoIdList) {
        return R.error().message("删除视频出错");
    }
}
