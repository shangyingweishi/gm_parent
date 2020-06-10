package com.gm.eduorder.client;

import com.gm.eduorder.entity.vo.CourseWebInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 远程调用service_edu服务的getCourseInfo方法
 */
@Component
@FeignClient(name = "service-edu")
public interface CourseClient {
    @GetMapping("/eduservice/coursefront/getCourseInfo/{courseId}")
    public CourseWebInfoVo getCourseInfo(@PathVariable("courseId") String courseId);

}
