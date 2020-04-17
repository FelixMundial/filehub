package com.example.filehub.service.user.security.config;

import com.example.filehub.service.user.security.filter.UserAuthenticationFilter;
import com.example.filehub.service.user.security.handler.UserAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author yinfelix
 * @date 2020/4/16
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/login", "/register", "/about").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").denyAll()
                .anyRequest().authenticated()
                // 自动登录信息暂存在Cookie中
                .and().rememberMe()
                // 禁用CSRF，开启跨域
                .and().csrf().disable().cors();

        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);

        http
                .addFilter(userAuthenticationFilter());
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    UserAuthenticationFilter userAuthenticationFilter() throws Exception {
        return new UserAuthenticationFilter(getAuthenticationManager());
    }

    @Autowired
    private UserAccessDeniedHandler accessDeniedHandler;
}
