package com.gm.eduservice.controller.front;

import com.gm.commonutils.R;
import com.gm.eduservice.entity.EduCourse;
import com.gm.eduservice.entity.EduTeacher;
import com.gm.eduservice.service.EduCourseService;
import com.gm.eduservice.service.EduSubjectService;
import com.gm.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/index")
//@CrossOrigin
public class FrontPageController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //获取教师前四天数据和课程前八条数据到前端页面展示
    @GetMapping("/getTeacherInfoList")
    @Cacheable(key = "'teacherList'", value = "teacher")
    public R getTeacherInfoList(){

        List<EduTeacher> teacherList = teacherService.getIndexTeachers();

        return R.ok().data("teacherList", teacherList);

    }

    @GetMapping("/getCourseInfoList")
    @Cacheable(key = "'courseList'", value = "course")
    public R getCourseInfoList(){

        List<EduCourse> courseList = courseService.getIndexCourses();

        return R.ok().data("courseList", courseList);
    }

}
