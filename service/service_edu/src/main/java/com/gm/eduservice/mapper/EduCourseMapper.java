package com.gm.eduservice.mapper;

import com.gm.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gm.eduservice.entity.vo.CoursePublishInfo;
import com.gm.eduservice.entity.vo.front.CourseWebInfoVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-05-26
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishInfo getPublishCourseInfo(String courseId);

    CourseWebInfoVo getFrontCourseInfo(String id);
}
