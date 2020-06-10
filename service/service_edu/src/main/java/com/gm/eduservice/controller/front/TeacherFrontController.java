package com.gm.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gm.commonutils.R;
import com.gm.eduservice.entity.EduCourse;
import com.gm.eduservice.entity.EduTeacher;
import com.gm.eduservice.service.EduCourseService;
import com.gm.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eduservice/teacherfront")
//@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //前端名师列表分页展示
    @PostMapping("/getTeacherList/{current}/{limit}")
    public R getTeacherList(@PathVariable int current, @PathVariable int limit){

        IPage<EduTeacher> teacherPageList = teacherService.getTeacherPageList(current, limit);

        return R.ok().data("total" ,teacherPageList.getTotal()).data("items", teacherPageList.getRecords());

    }

    //根据讲师id查询讲师课程
    @GetMapping("/getCourseList/{teacherId}")
    public R getCourseList(@PathVariable String teacherId){

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        wrapper.eq("teacher_id", teacherId);

        List<EduCourse> courseList = courseService.list(wrapper);

        return R.ok().data("courseList", courseList);

    }

}
