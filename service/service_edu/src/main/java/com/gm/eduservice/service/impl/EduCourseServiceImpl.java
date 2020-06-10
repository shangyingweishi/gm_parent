package com.gm.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gm.eduservice.entity.*;
import com.gm.eduservice.entity.vo.front.CourseFrontVo;
import com.gm.eduservice.entity.vo.CourseInfoForm;
import com.gm.eduservice.entity.vo.CourseListInfo;
import com.gm.eduservice.entity.vo.CoursePublishInfo;
import com.gm.eduservice.entity.vo.front.CourseWebInfoVo;
import com.gm.eduservice.mapper.EduCourseMapper;
import com.gm.eduservice.query.CourseQuery;
import com.gm.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gm.eduservice.springcloudclient.VideoClient;
import com.gm.servicebase.entity.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduSubjectService eduSubjectService;

    @Autowired
    private VideoClient videoClient;

    @Override
    public String addCourseInfo(CourseInfoForm courseInfoForm) {
        //向edu_course表中添加数据
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm, eduCourse);
        eduCourse.setIsDeleted(0);
        int i = baseMapper.insert(eduCourse);

        if (i <= 0){
            throw new MyException(20001, "添加课程失败");
        }

        //向edu_course_description中添加数据
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescription.setId(eduCourse.getId());
        boolean b = eduCourseDescriptionService.save(eduCourseDescription);

        if (!b){
            throw new MyException(20001, "添加课程信息失败");
        }

        return eduCourse.getId();
    }

    @Override
    public CourseInfoForm getCourseById(String courseId) {

        EduCourse eduCourse = baseMapper.selectById(courseId);

        CourseInfoForm courseInfo = new CourseInfoForm();

        BeanUtils.copyProperties(eduCourse, courseInfo);


        EduCourseDescription one = eduCourseDescriptionService.getById(courseId);
        courseInfo.setDescription(one.getDescription());

        return courseInfo;
    }

    @Override
    public Boolean updateCourseInfo(CourseInfoForm courseInfoForm) {

        EduCourse eduCourse = new EduCourse();

        BeanUtils.copyProperties(courseInfoForm, eduCourse);

        int i = baseMapper.updateById(eduCourse);

        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(courseInfoForm.getId());
        boolean b = eduCourseDescriptionService.updateById(courseDescription);

        if(i > 0 && b == true){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public CoursePublishInfo getPublishInfo(String courseId) {

        CoursePublishInfo publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);

        return publishCourseInfo;
    }

    @Override
    public Boolean pulishCourse(String courseId) {

        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        int i = baseMapper.updateById(eduCourse);


        return i > 0;
    }

    @Override
    public boolean removeCourse(String courseId) {

        //删除小节同时删除阿里云视频
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> list = eduVideoService.list(videoQueryWrapper);
        List<String> videoSourceIdList = new ArrayList<>();
        for (EduVideo eduVideo : list) {
            if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
                videoSourceIdList.add(eduVideo.getVideoSourceId());
            }
        }
        if (videoSourceIdList.size() > 0){
            videoClient.delBatch(videoSourceIdList);
        }

        boolean remove1 = eduVideoService.remove(videoQueryWrapper);

        //删除章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        boolean remove2 = eduChapterService.remove(chapterQueryWrapper);

        //删除描述
        QueryWrapper<EduCourseDescription> descriptionQueryWrapper = new QueryWrapper<>();
        descriptionQueryWrapper.eq("id", courseId);
        boolean remove3 = eduCourseDescriptionService.remove(descriptionQueryWrapper);

        //删除课程
        int i = baseMapper.deleteById(courseId);


        return remove1 && remove2 && remove3 && i > 0;
    }

    @Override
    public IPage<EduCourse> getCourseListCondition(int current, int limit, CourseQuery courseQuery) {

        Page<EduCourse> coursePage = new Page<>(current, limit);

        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(courseQuery.getTitle())){
            courseQueryWrapper.like("title", courseQuery.getTitle());
        }
        if(!StringUtils.isEmpty(courseQuery.getTeacherId())){
            courseQueryWrapper.eq("teacher_id", courseQuery.getTeacherId());
        }
        if(!StringUtils.isEmpty(courseQuery.getSubjectParentId())){
            courseQueryWrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseQuery.getSubjectId())){
            courseQueryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }

        courseQueryWrapper.orderByDesc("gmt_create");


        IPage<EduCourse> eduCourseIPage = baseMapper.selectPage(coursePage, courseQueryWrapper);



       return eduCourseIPage;
    }

    @Override
    public List<CourseListInfo> getCourseListInfo() {

        List<EduCourse> courseList = baseMapper.selectList(null);

        List<CourseListInfo> courseListInfos = changeToCourseListInfo(courseList);

        return courseListInfos;
    }

    @Override
    public List<EduCourse> getIndexCourses() {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        wrapper.orderByDesc("id");

        wrapper.last("limit 8");

        List<EduCourse> courseList = baseMapper.selectList(wrapper);

        if (courseList.size() > 0){
            return courseList;
        }else {
            throw new MyException(20001, "没有数据!请联系管理员...");
        }
    }

    //前端页面带分页的条件查询
    @Override
    public IPage<EduCourse> getFrontCourseCondition(int current, int limit, CourseFrontVo courseFrontVo) {

        Page<EduCourse> page = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String subjectParentId = courseFrontVo.getSubjectParentId();
        String subjectId = courseFrontVo.getSubjectId();
        String priceSort = courseFrontVo.getPriceSort();
        String buyCountSort = courseFrontVo.getBuyCountSort();
        String gmtCreateSort = courseFrontVo.getGmtCreateSort();
        if (!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(priceSort)){
            wrapper.orderByDesc("price");
        }
        if (!StringUtils.isEmpty(buyCountSort)){
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(gmtCreateSort)){
            wrapper.orderByDesc("gmt_create");
        }
        IPage<EduCourse> eduCourseIPage = baseMapper.selectPage(page, wrapper);

        return eduCourseIPage;
    }

    @Override
    public CourseWebInfoVo getFrontCourseInfo(String id) {

        CourseWebInfoVo courseWebInfoVo = baseMapper.getFrontCourseInfo(id);
        return courseWebInfoVo;
    }

    @Override
    public List<CourseListInfo> changeToCourseListInfo(List<EduCourse> courseList){

        List<CourseListInfo> courseListInfos = new ArrayList<>();
        for (EduCourse eduCourse : courseList) {
            CourseListInfo courseListInfo = new CourseListInfo();
            BeanUtils.copyProperties(eduCourse, courseListInfo);
            courseListInfo.setCourseName(eduCourse.getTitle());

            EduTeacher teacher = eduTeacherService.getById(eduCourse.getTeacherId());
            if (teacher == null){
                courseListInfo.setTeacherName("未确定讲师");
            }else {
                courseListInfo.setTeacherName(teacher.getName());
            }

            EduSubject subjectOne = eduSubjectService.getById(eduCourse.getSubjectParentId());
            if (subjectOne == null){
                courseListInfo.setSubjectParentName("一级分类消失了");
            }else {
                courseListInfo.setSubjectParentName(subjectOne.getTitle());
            }

            EduSubject subjectTwo = eduSubjectService.getById(eduCourse.getSubjectId());
            if (subjectTwo == null){
                courseListInfo.setSubjectName("二级分类消失了");
            }else {
                courseListInfo.setSubjectName(subjectTwo.getTitle());
            }


            courseListInfos.add(courseListInfo);
        }

        return courseListInfos;

    }
}
