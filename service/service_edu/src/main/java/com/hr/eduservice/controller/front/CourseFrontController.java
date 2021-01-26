package com.hr.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hr.commonutils.JwtUtils;
import com.hr.commonutils.R;
import com.hr.commonutils.ordervo.CourseWebVoOrder;
import com.hr.eduservice.client.OrdersClient;
import com.hr.eduservice.entity.EduCourse;
import com.hr.eduservice.entity.chapter.ChapterVo;
import com.hr.eduservice.entity.vo.CourseFrontVo;
import com.hr.eduservice.entity.vo.CourseWebVo;
import com.hr.eduservice.service.EduChapterService;
import com.hr.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@Api("前台前端课程")
@CrossOrigin
public class CourseFrontController {


    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private OrdersClient ordersClient;


    @ApiOperation("分页查询课程")
    @PostMapping("/getCourseFrontList/{page}/{limit}")
    public R getCourseFrontList(@PathVariable Integer page,
                                @PathVariable Integer limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo
                                ) {
        Page<EduCourse> eduCoursePage = new Page<>(page, limit);
        Map<String,Object> map =  eduCourseService.getCourseFrontList(eduCoursePage, courseFrontVo);
        return R.ok().data(map);
    }


    @ApiOperation("课程详情")
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        //查询课程基本信息
        CourseWebVo courseWebVo = eduCourseService.getFrontCourseInfo(courseId);
         //查询章节和小节
        List<ChapterVo> chapterVoList = eduChapterService.getChapterVideoByCourseId(courseId);
        //根据课程id和用户id查询当前课程是否已经支付过了
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        Boolean isBuy = ordersClient.isBuyCourse(courseId, memberIdByJwtToken);

        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVoList).data("isBuy", isBuy);
    }


    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
        //查询课程基本信息
        CourseWebVo courseWebVo = eduCourseService.getFrontCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseWebVo, courseWebVoOrder);
        return courseWebVoOrder;
    }




}
