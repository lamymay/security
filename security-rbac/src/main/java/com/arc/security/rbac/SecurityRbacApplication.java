package com.arc.security.rbac;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.arc.security.rbac.mapper")
@SpringBootApplication
        (exclude = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class

        })
public class SecurityRbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityRbacApplication.class, args);
    }

}
