package com.gitee.pristine.web;

import com.gitee.pristine.security.enc.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class StrawsWebApplicationTests {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {

        String encode = passwordEncoder.encode("123456", "as66jk");
        System.out.println(encode);

    }

}
