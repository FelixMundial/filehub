package com.example.filehub.service.gateway.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @apiNote 注册CorsFilter
 * @author yinfelix
 * @date 2020/4/28
 */
@Configuration
public class GatewayConfiguration {
    @Bean
    public FilterRegistrationBean corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // 允许cookies跨域
        config.addAllowedOrigin("*"); // 允许向该服务器提交请求的URI，*表示全部允许。这里尽量限制来源域，比如http://xxxx:8080，以降低安全风险。。
        config.addAllowedHeader("*"); // 允许访问的头信息,*表示全部
        config.setMaxAge(1800L); // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.addAllowedMethod("*"); // 允许提交请求的方法，*表示全部允许，也可以单独设置GET、PUT等
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        /*
        置于Spring Security所有过滤器之前
         */
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
