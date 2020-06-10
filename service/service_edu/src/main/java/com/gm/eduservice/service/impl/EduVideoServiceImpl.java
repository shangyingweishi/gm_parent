package com.gm.eduservice.service.impl;

import com.gm.commonutils.R;
import com.gm.eduservice.entity.EduVideo;
import com.gm.eduservice.mapper.EduVideoMapper;
import com.gm.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gm.eduservice.springcloudclient.VideoClient;
import com.gm.servicebase.entity.MyException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-05-26
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    //注入VideoClient
    @Autowired
    private VideoClient videoClient;

    @Override
    public Boolean saveVideo(EduVideo eduVideo) {

        int i = baseMapper.insert(eduVideo);

        return i > 0;
    }

    //    删除小节，同时远程调用service-video服务删除阿里云里面的视频
    @Override
    public Boolean delVideoById(String videoId) {

        EduVideo eduVideo = baseMapper.selectById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();

        if (!StringUtils.isEmpty(videoSourceId)) {
            R r = videoClient.delVideo(videoSourceId);
            if(r.getCode() == 20001 ){
                throw new MyException(20001, "删除视频失败，熔断器操作...");
            }
        }
        int i = baseMapper.deleteById(videoId);

        return i > 0;
    }

    @Override
    public Boolean updateVideo(EduVideo eduVideo) {

        int i = baseMapper.updateById(eduVideo);

        return i > 0;
    }

    @Override
    public EduVideo getVideoInfo(String videoId) {

        EduVideo eduVideo = baseMapper.selectById(videoId);

        return eduVideo;
    }
}
