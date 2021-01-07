package com.hr.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
public class CourseQuery {

    @ApiModelProperty("课程名称")
    private String title;

    @ApiModelProperty("讲师id")
    private String teacherId;

    @ApiModelProperty("一级类别id")
    private String subjectParentId;

    @ApiModelProperty("二级类别id")
    private String subjectId;

}
