package com.hr.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.hr.eduorder.mapper")
@ComponentScan("com.hr")
@EnableDiscoveryClient   //远程调用
public class OrderApplicaton {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplicaton.class, args);
    }
}
