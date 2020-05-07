package com.example.filehub.service.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.filehub.service.comment", "com.example.filehub.common.service"})
public class CommentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentServiceApplication.class, args);
    }

}
