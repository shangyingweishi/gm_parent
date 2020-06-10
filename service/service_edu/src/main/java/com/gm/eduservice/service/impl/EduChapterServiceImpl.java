package com.gm.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gm.eduservice.entity.EduChapter;
import com.gm.eduservice.entity.EduCourse;
import com.gm.eduservice.entity.EduVideo;
import com.gm.eduservice.entity.chapter.CourseChatper;
import com.gm.eduservice.entity.chapter.CourseVideo;
import com.gm.eduservice.mapper.EduChapterMapper;
import com.gm.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gm.eduservice.service.EduVideoService;
import com.gm.servicebase.entity.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-05-26
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<CourseChatper> getChapterList(String courseId) {

        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        videoQueryWrapper.eq("course_id", courseId);

        List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapper);
        List<EduVideo> eduVideos = eduVideoService.list(videoQueryWrapper);


        List<CourseChatper> chatperList = new ArrayList<>();
        for (EduChapter eduChapter : eduChapters) {
            CourseChatper courseChatper = new CourseChatper();
            BeanUtils.copyProperties(eduChapter, courseChatper);
            chatperList.add(courseChatper);

            List<CourseVideo> videoList = new ArrayList<>();
            for (EduVideo eduVideo : eduVideos) {
                CourseVideo courseVideo = new CourseVideo();
                if(eduChapter.getId().equals(eduVideo.getChapterId())){
                    BeanUtils.copyProperties(eduVideo, courseVideo);
                    videoList.add(courseVideo);
                }
            }

            courseChatper.setVideoList(videoList);
        }

        return chatperList;
    }

    @Override
    public Boolean saveChapter(EduChapter eduChapter) {

        int insert = baseMapper.insert(eduChapter);

        return insert > 0;
    }

    @Override
    public EduChapter getChapter(String chapterId) {

        EduChapter eduChapter = baseMapper.selectById(chapterId);

        return eduChapter;
    }


    @Override
    public Boolean updateChapter(EduChapter eduChapter) {

        int i = baseMapper.updateById(eduChapter);

        return i > 0;
    }

    @Override
    public Boolean delChapter(String id) {

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", id);
        int count = eduVideoService.count(wrapper);
        if (count > 0){
            throw new MyException(20001, "不能删除");
        }else {
            int i = baseMapper.deleteById(id);
            return i > 0;
        }


    }




}
