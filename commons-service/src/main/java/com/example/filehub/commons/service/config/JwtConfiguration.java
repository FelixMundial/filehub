package com.example.filehub.commons.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author yinfelix
 * @date 2020/4/22
 */
//@Configuration
public class JwtConfiguration {
    /*
    暂时采用对称加密（应以端点形式配置在配置文件中）
     */
//    @Value("security.oauth2.resource.jwt.key-value")
    public static final String SIGNING_KEY = "uaa123";

    @Primary
    @Bean(value = "customJwtTokenStore")
    public JwtTokenStore customJwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(SIGNING_KEY);
        return accessTokenConverter;
    }
}
