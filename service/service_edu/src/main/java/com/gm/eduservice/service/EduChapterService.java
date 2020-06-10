package com.gm.eduservice.service;

import com.gm.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gm.eduservice.entity.chapter.CourseChatper;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-05-26
 */
public interface EduChapterService extends IService<EduChapter> {

    List<CourseChatper> getChapterList(String courseId);
    

    Boolean updateChapter(EduChapter eduChapter);

    Boolean delChapter(String id);

    Boolean saveChapter(EduChapter eduChapter);

    EduChapter getChapter(String chapterId);
}
