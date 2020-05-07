package com.example.filehub.service.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.filehub.service.file", "com.example.filehub.common.service"})
public class FileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileServiceApplication.class, args);
    }

}
