package com.example.filehub.service.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.example.filehub.commons.entity")
@SpringBootApplication(scanBasePackages = {"com.example.filehub.service.library", "com.example.filehub.common.service"})
public class LibraryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryServiceApplication.class, args);
    }

}
