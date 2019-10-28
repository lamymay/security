package com.arc.security.browser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@MapperScan("com.arc.security.rbac.mapper")
//@ComponentScan({"com.arc.security.rbac.service", "com.arc.security.rbac.mapper"})
@SpringBootApplication(exclude = {



})
public class SecurityBrowserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityBrowserApplication.class, args);
    }

}
