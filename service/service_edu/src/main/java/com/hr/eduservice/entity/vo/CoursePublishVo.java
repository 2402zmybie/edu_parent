package com.hr.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo {

    private String id;
    private String title;
    private String cover;
    private String description;
    private Integer lessonNum;
    private String price;//只用于显示
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;

}
