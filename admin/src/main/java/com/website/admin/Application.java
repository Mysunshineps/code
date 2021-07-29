package com.website.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Description
 * @Author psq
 * @Date 2021/6/18/14:32
 */
@SpringBootApplication
@MapperScan("com.website.mapper")
@ComponentScan("com.website")
public class Application {
     void test(){

     }
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
