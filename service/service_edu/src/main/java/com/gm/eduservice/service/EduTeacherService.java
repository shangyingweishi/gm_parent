package com.gm.eduservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gm.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-05-15
 */
public interface EduTeacherService extends IService<EduTeacher> {


    List<EduTeacher> getIndexTeachers();

    IPage<EduTeacher> getTeacherPageList(int current, int limit);
}
