package com.hr.servicebase.exceptionhandler;

import com.hr.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一的异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHanlder {

    //指定出现什么异常执行方法
    @ExceptionHandler(Exception.class)
    //返回数据
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    //指定特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了除零异常");
    }


    //指定自定义异常处理
    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R error(EduException e) {
        e.printStackTrace();
        //写入到error的日志文件
        log.error(e.getMessage());
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
