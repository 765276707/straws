package com.gitee.pristine.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.gitee.pristine")
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.gitee.pristine"})
@MapperScan(basePackages = {"com.gitee.pristine.web.mapper"})
@EnableAsync
public class StrawsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrawsWebApplication.class, args);
    }

}
