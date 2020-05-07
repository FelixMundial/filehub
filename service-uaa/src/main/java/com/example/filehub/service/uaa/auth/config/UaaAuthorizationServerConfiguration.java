package com.example.filehub.service.uaa.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author yinfelix
 * @date 2020/3/26
 */
@EnableAuthorizationServer
@Configuration
public class UaaAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Qualifier(BeanIds.USER_DETAILS_SERVICE)
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore customJwtTokenStore;

    @Qualifier(value = BeanIds.AUTHENTICATION_MANAGER)
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * @return token服务信息
     */
    @Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);

        tokenServices.setTokenStore(customJwtTokenStore);
        tokenServices.setAccessTokenValiditySeconds(3600);
        tokenServices.setRefreshTokenValiditySeconds(14400);

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));
        tokenServices.setTokenEnhancer(tokenEnhancerChain);

        return tokenServices;
    }

    /**
     * ClientDetails信息从数据库获取（也可存储于内存中）
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    /**
     * 配置令牌访问端点及令牌服务
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenServices(authorizationServerTokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    /**
     * 配置令牌访问端点安全约束
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                /*
                oauth/token_key
                 */
                .tokenKeyAccess("permitAll()")
                /*
                oauth/check_token
                 */
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }
}
