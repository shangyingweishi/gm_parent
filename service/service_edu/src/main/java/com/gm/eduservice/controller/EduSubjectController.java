package com.gm.eduservice.controller;


import com.gm.commonutils.R;
import com.gm.eduservice.entity.subject.OneSubject;
import com.gm.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-26
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("/saveSubject")
    public R saveExcel(MultipartFile file){

        eduSubjectService.addSubject(file, eduSubjectService);

        return R.ok();
    }

    @GetMapping("/getAllSubject")
    public R getAllSubject(){

        List<OneSubject> list = eduSubjectService.getSubjectList();

        return R.ok().data("list", list);
    }

}

