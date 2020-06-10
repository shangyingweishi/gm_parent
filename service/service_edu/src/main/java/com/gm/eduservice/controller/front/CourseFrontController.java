package com.gm.eduservice.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gm.commonutils.JWTUtils;
import com.gm.commonutils.R;
import com.gm.eduservice.entity.EduCourse;
import com.gm.eduservice.entity.chapter.CourseChatper;
import com.gm.eduservice.entity.vo.CourseInfoForm;
import com.gm.eduservice.entity.vo.front.CourseFrontVo;
import com.gm.eduservice.entity.vo.front.CourseWebInfoVo;
import com.gm.eduservice.service.EduChapterService;
import com.gm.eduservice.service.EduCourseService;
import com.gm.eduservice.springcloudclient.OrderClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/eduservice/coursefront")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    //条件查询带分页
    @PostMapping("/getFrontCourseCondition/{current}/{limit}")
    public R getFrontCourseCondition(@PathVariable int current, @PathVariable int limit, @RequestBody(required = false) CourseFrontVo courseFrontVo) {

        IPage<EduCourse> eduCourseIPage = courseService.getFrontCourseCondition(current, limit, courseFrontVo);

        return R.ok().data("total", eduCourseIPage.getTotal()).data("courseList", eduCourseIPage.getRecords());

    }

    //课程详细信息页面展示
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){


        CourseWebInfoVo courseWebInfoVo = courseService.getFrontCourseInfo(courseId);

        List<CourseChatper> chapterList = chapterService.getChapterList(courseId);

        Boolean isBuy = orderClient.checkPayStatus(courseId, JWTUtils.getMemberIdByJwtToken(request));

        System.out.println(isBuy);
        return R.ok().data("chapterList",chapterList).data("courseInfo", courseWebInfoVo).data("isBuy", isBuy);

    }

    //根据课程id得到课程信息供order远程调用
    @GetMapping("/getCourseInfo/{courseId}")
    public CourseWebInfoVo getCourseInfo(@PathVariable String courseId){
        CourseWebInfoVo courseInfo = courseService.getFrontCourseInfo(courseId);

        return courseInfo;
    }

}
