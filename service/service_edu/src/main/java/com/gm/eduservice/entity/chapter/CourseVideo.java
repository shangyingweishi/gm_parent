package com.gm.eduservice.entity.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseVideo {

    private String id;
    private String title;

    @ApiModelProperty(value = "是否可以试听：0收费 1免费")
    private Boolean isFree;

    @ApiModelProperty(value = "云端视频资源")
    private String videoSourceId;

}
