package com.mango.mpplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mango.mpplus.mapper")
public class MpplusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpplusApplication.class, args);
    }

}
