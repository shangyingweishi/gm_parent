package com.gm.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CourseListInfo {

    private String id;

    private String courseName;

    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String status;

    private String teacherName;

    @ApiModelProperty(value = "一级分类名")
    private String subjectParentName;

    @ApiModelProperty(value = "二级分类名")
    private String subjectName;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    private Date gmtCreate;

}
