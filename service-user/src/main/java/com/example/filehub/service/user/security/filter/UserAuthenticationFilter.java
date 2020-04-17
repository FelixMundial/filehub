package com.example.filehub.service.user.security.filter;

import com.example.filehub.commons.service.entity.UserAccountInfo;
import com.example.filehub.commons.service.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author yinfelix
 * @date 2020/3/9
 */
@Slf4j
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;

    private UserAccountInfo user;

    public UserAuthenticationFilter(AuthenticationManager authenticationManager) {
        Assert.notNull(authenticationManager, "UserAuthenticationFilter构造器：authenticationManager cannot be null");
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public void afterPropertiesSet() {
        setAuthenticationSuccessHandler(successHandler);
        setAuthenticationFailureHandler(failureHandler);
    }

    private UserAccountInfo retrieveUser(HttpServletRequest request) {
        if (this.user == null) {
            try {
                user = JsonUtils.getPojoFromInputStream(request.getInputStream(), UserAccountInfo.class);
                if (user == null) {
                    logger.error("retrieveUser(): User cannot be found in request.");
                }
            } catch (IOException e) {
                logger.error("retrieveUser(): {}", e);
                throw new AuthenticationServiceException(e.getMessage());
            }
        }
        return this.user;
    }

    @Override
    public String obtainUsername(HttpServletRequest request) {
        /*
        json格式请求体
         */
        if (retrieveUser(request) != null) {
            return user.getUserLoginName();
        }
        return null;
    }

    @Override
    public String obtainPassword(HttpServletRequest request) {
        if (retrieveUser(request) != null) {
            return user.getUserLoginPassword();
        }
        return null;
    }
}
