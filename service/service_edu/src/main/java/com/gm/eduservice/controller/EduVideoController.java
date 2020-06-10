package com.gm.eduservice.controller;


import com.gm.commonutils.R;
import com.gm.eduservice.entity.EduVideo;
import com.gm.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-26
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
@Api(description = "小节管理")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){

        Boolean b = eduVideoService.saveVideo(eduVideo);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @DeleteMapping("/delVideo/{videoId}")
    public R delVideo(@PathVariable String videoId){

        Boolean b = eduVideoService.delVideoById(videoId);

        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){

        Boolean b = eduVideoService.updateVideo(eduVideo);

        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("/getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId){

        EduVideo eduVideo = eduVideoService.getVideoInfo(videoId);

        return R.ok().data("videoInfo", eduVideo);
    }

}

