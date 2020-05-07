package com.example.filehub.common.service.config;

import com.example.filehub.common.service.filter.AuthorizationFilter;
import com.example.filehub.common.service.handler.UserAccessDeniedHandler;
import com.example.filehub.common.service.handler.UserAuthenticationFailureEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author yinfelix
 * @date 2020/4/26
 */
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    public static final String RESOURCE_ID = "resource1";

    @Autowired
    private TokenStore customJwtTokenStore;

    @Autowired
    private AuthorizationFilter authorizationFilter;

    @Autowired
    private RequestUrlFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;
    @Autowired
    private RequestUrlAccessDecisionManager accessDecisionManager;
    @Autowired
    private UserAuthenticationFailureEntryPoint authenticationFailureEntryPoint;
    @Autowired
    private UserAccessDeniedHandler accessDeniedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/**")
//                .access("#oauth2.hasAnyScope('role_admin', 'role_user', 'role_guest')")
                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
                        o.setAccessDecisionManager(accessDecisionManager);
                        return o;
                    }
                });

        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationFailureEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)
                .tokenStore(customJwtTokenStore)
        /*.stateless(true)*/;
    }
}
