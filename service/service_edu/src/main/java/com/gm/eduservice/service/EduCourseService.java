package com.gm.eduservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gm.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gm.eduservice.entity.vo.front.CourseFrontVo;
import com.gm.eduservice.entity.vo.CourseInfoForm;
import com.gm.eduservice.entity.vo.CourseListInfo;
import com.gm.eduservice.entity.vo.CoursePublishInfo;
import com.gm.eduservice.entity.vo.front.CourseWebInfoVo;
import com.gm.eduservice.query.CourseQuery;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-05-26
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseById(String courseId);

    Boolean updateCourseInfo(CourseInfoForm courseInfoForm);

    CoursePublishInfo getPublishInfo(String courseId);

    Boolean pulishCourse(String courseId);

    boolean removeCourse(String courseId);

    IPage<EduCourse> getCourseListCondition(int current, int limit, CourseQuery courseQuery);

    List<CourseListInfo> changeToCourseListInfo(List<EduCourse> courseList);

    List<CourseListInfo> getCourseListInfo();

    List<EduCourse> getIndexCourses();

    IPage<EduCourse> getFrontCourseCondition(int current, int limit, CourseFrontVo courseFrontVo);

    CourseWebInfoVo getFrontCourseInfo(String id);
}
