package com.gm.eduservice.service;

import com.gm.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-05-26
 */
public interface EduVideoService extends IService<EduVideo> {

    Boolean saveVideo(EduVideo eduVideo);

    Boolean delVideoById(String videoId);

    Boolean updateVideo(EduVideo eduVideo);

    EduVideo getVideoInfo(String videoId);
}
