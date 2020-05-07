package com.example.filehub.service.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
若需扫描其他模块的JPA接口，只能通过@EnableJpaRepositories进行扫描
 */
//@EnableJpaRepositories(basePackages={"com.example.filehub.commons.service.dao.user"})
//@EnableTransactionManagement
@EntityScan("com.example.filehub.commons.entity.user")
@SpringBootApplication
public class UaaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UaaServiceApplication.class, args);
    }

}
