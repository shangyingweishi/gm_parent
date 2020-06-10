package com.gm.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CourseChatper {

    private String id;
    private String title;

    List<CourseVideo> videoList = new ArrayList<>();

}
