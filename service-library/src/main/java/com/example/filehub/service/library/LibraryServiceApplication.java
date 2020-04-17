package com.example.filehub.service.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//JPA所需注解，暂时去除
//@EntityScan("com.example.filehub.commons.service.entity")
@SpringBootApplication
public class LibraryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryServiceApplication.class, args);
    }

}
