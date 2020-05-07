package com.example.filehub.service.uaa.auth.config;

import com.example.filehub.service.uaa.auth.filter.UaaAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author yinfelix
 * @date 2020/4/16
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class UaaWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    UaaAuthenticationFilter uaaAuthenticationFilter() throws Exception {
        return new UaaAuthenticationFilter(getAuthenticationManager());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/uaa/login/**", "/register/**", "/oauth/**").permitAll()
                .anyRequest().authenticated()
                // 自动登录信息暂存在Cookie中
//                .and().rememberMe()
                .and().formLogin().loginProcessingUrl("/login")
                .and().csrf().disable()/*.cors()*/;

        http
                .addFilter(uaaAuthenticationFilter());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/oauth/check_token");
    }
}
