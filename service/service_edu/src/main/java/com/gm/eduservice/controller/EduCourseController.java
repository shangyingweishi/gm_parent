package com.gm.eduservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gm.commonutils.R;
import com.gm.eduservice.entity.EduCourse;
import com.gm.eduservice.entity.vo.CourseInfoForm;
import com.gm.eduservice.entity.vo.CourseListInfo;
import com.gm.eduservice.entity.vo.CoursePublishInfo;
import com.gm.eduservice.query.CourseQuery;
import com.gm.eduservice.service.EduCourseService;
import com.gm.servicebase.entity.MyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-26
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
@Api(description = "课程管理")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/addCourse")
    @ApiOperation(value = "添加课程信息")
    public R addCourse(@RequestBody CourseInfoForm courseInfoForm){


        String id = eduCourseService.addCourseInfo(courseInfoForm);

        return R.ok().data("id", id);
    }

    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){

        CourseInfoForm courseInfo = eduCourseService.getCourseById(courseId);

        return R.ok().data("courseInfo", courseInfo);
    }

    @PostMapping("/updateCourseInfo")
    public R editCourseInfo(@RequestBody CourseInfoForm courseInfoForm){

        Boolean flag = eduCourseService.updateCourseInfo(courseInfoForm);

        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }

    @GetMapping("/getCoursePublishInfo/{courseId}")
    public R getCoursePublishInfo(@PathVariable String courseId){

        CoursePublishInfo coursePublishInfo = eduCourseService.getPublishInfo(courseId);

        return R.ok().data("publishInfo", coursePublishInfo);

    }

    @PostMapping("/publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId){

        Boolean b = eduCourseService.pulishCourse(courseId);

        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("/courseList")
    public R getCourseList(){

        List<CourseListInfo> courseListInfos = eduCourseService.getCourseListInfo();

        return R.ok().data("courseListInfo",courseListInfos);
    }

    @DeleteMapping("/delCourse/{courseId}")
    public R delCourse(@PathVariable String courseId){

        boolean b = eduCourseService.removeCourse(courseId);

        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //课程条件查询带分页展示
    @PostMapping("/courseCondition/{current}/{limit}")
    public R getCourseListCondition(@PathVariable int current, @PathVariable int limit, @RequestBody(required = false) CourseQuery courseQuery){

        IPage<EduCourse> courseListCondition = eduCourseService.getCourseListCondition(current, limit, courseQuery);

        List<EduCourse> records = courseListCondition.getRecords();

        List<CourseListInfo> courseListInfos = eduCourseService.changeToCourseListInfo(records);

        return R.ok().data("total", courseListCondition.getTotal()).data("courseListInfos", courseListInfos);
    }



}

