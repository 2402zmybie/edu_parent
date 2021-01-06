package com.hr.eduservice.mapper;

import com.hr.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-01-04
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    @Select("select * from edu_course where id=#{id}")
    EduCourse mySelectById(String id);


}
