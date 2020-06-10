package com.gm.video.controller;

import com.gm.commonutils.R;
import com.gm.video.service.VideoService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvideo/uploadvideo")
@CrossOrigin
@Api(description = "上传视频")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/upload")
    public R uploadVideo(MultipartFile file){

        String videoId = videoService.uploadVideo(file);

        return R.ok().data("videoId", videoId);

    }

    //删除指定视频
    @DeleteMapping("/remove/{videoSourceId}")
    public R delVideo(@PathVariable String videoSourceId){

        String delVideoId = videoService.delVideo(videoSourceId);

        if (!StringUtils.isEmpty(delVideoId)){
            return R.ok();
        }else {
            return R.error();
        }

    }

    //删除多个视频
    @DeleteMapping("/removeBatch")
    public R delBatch(@RequestParam List<String> videoIdList){
        videoService.delBatch(videoIdList);

        return R.ok();
    }

    //通过视频id获取视频凭证
    @GetMapping("/getVideoAuth/{id}")
    public R getVideoAuth(@PathVariable String id){

        String videoAuth = videoService.getVideoAuth(id);

        return R.ok().data("auth", videoAuth);
    }

}
