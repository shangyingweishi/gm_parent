package com.gm.eduservice.controller;


import com.gm.commonutils.R;
import com.gm.eduservice.entity.EduChapter;
import com.gm.eduservice.entity.chapter.CourseChatper;
import com.gm.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-26
 */
@RestController
@RequestMapping("/eduservice/chapter")
//@CrossOrigin
@Api(description = "章节管理")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("/getChapterInfo/{courseId}")
    public R getChapterInfo(@PathVariable String courseId){

        List<CourseChatper> chatperList = eduChapterService.getChapterList(courseId);

        return R.ok().data("chatperList", chatperList);

    }

    @PostMapping("/addChapterInfo")
    public R addChapterInfo(@RequestBody EduChapter eduChapter){

        Boolean b = eduChapterService.saveChapter(eduChapter);

        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("/updateChapterInfo")
    public R updateChapterInfo(@RequestBody EduChapter eduChapter){

        Boolean b = eduChapterService.updateChapter(eduChapter);

        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("/getChapterById/{chapterId}")
    public R getChapterById(@PathVariable String chapterId){

        EduChapter eduChapter = eduChapterService.getChapter(chapterId);

        return R.ok().data("chapterInfo", eduChapter);

    }

    @DeleteMapping("/delChapter/{id}")
    public R delChapter(@PathVariable String id){

        Boolean b = eduChapterService.delChapter(id);

        if (b){
            return R.ok();
        }else {
            return R.error();
        }

    }


}

