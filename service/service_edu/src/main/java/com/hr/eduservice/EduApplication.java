package com.hr.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hr"})
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class);

        //注：char只能放单个字符。
        char a = 'a';  //任意单个字符，加单引号。

        char b = '中'; //任意单个中文字，加单引号。

        char c = 111; //整数。0~65535。十进制、八进制、十六进制均可。输出字符编码表中对应的字符。
        System.out.println("a:" + a + "\n" + "b:" + b + "\n" + "c:" + c);

        //注：String可以放多个
        String str = "aaaaa";
        String str2 = "中中中中";
        String str3 = "111111111111";

    }
}
