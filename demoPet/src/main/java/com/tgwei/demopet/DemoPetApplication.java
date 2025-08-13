package com.tgwei.demopet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tgwei.demopet.demos.web.mapper")
public class DemoPetApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoPetApplication.class, args);
    }

}
