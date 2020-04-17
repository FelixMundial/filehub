package com.example.filehub.service.user;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.util.Objects;

@EntityScan("com.example.filehub.commons.service.entity")
@SpringBootApplication
public class UserServiceApplication {

    @Bean
    public static PropertySourcesPlaceholderConfigurer rsaPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer rsaPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("rsa.yml"));
        rsaPlaceholderConfigurer.setProperties(Objects.requireNonNull(yaml.getObject()));
        return rsaPlaceholderConfigurer;
    }

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
