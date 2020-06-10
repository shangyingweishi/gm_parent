package com.gm.eduservice.service;

import com.gm.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gm.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-05-26
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<OneSubject> getSubjectList();
}
