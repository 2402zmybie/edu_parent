package com.hr.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.hr")
@MapperScan("com.hr.educenter.mapper")
@EnableDiscoveryClient
public class UCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UCenterApplication.class,args);
    }
}
