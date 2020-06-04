package com.example.filehub.service.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @apiNote 对接入网关的请求进行过滤
 * @author yinfelix
 * @date 2020/4/24
 */
//@EnableOAuth2Sso //SSO跳转暂时由前端接管
@EnableResourceServer // 当不需要网关协助进行SSO时，网关须标记为资源服务器
@Configuration
public class GatewayResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    /**
     * 网关对匿名用户放行认证请求
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .antMatchers("/uaa/login/**").permitAll()
                /*
                暂时在网关全部放行
                 */
                .anyRequest().permitAll()
                .and().csrf().disable();
    }
}
