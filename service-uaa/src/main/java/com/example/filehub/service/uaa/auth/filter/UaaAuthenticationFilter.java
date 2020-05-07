package com.example.filehub.service.uaa.auth.filter;

import com.example.filehub.commons.entity.user.UserAccountInfo;
import com.example.filehub.commons.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author yinfelix
 * @date 2020/3/9
 */
@Slf4j
public class UaaAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;

    private UserAccountInfo user;

    public UaaAuthenticationFilter(AuthenticationManager authenticationManager) {
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
                user = JsonUtil.getPojoFromInputStream(request.getInputStream(), UserAccountInfo.class);
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
        String username = request.getParameter("username");
        /*
        json格式请求体
         */
        if (StringUtils.isEmpty(username)) {
            if (retrieveUser(request) != null) {
                return user.getUserLoginName();
            }
        }
        /*
        x-www-form-urlencoded格式请求体
         */
        else {
            return username;
        }
        return null;
    }

    @Override
    public String obtainPassword(HttpServletRequest request) {
        String password = request.getParameter("password");
        if (StringUtils.isEmpty(password)) {
            if (retrieveUser(request) != null) {
                return user.getUserLoginPassword();
            }
        } else {
            return password;
        }
        return null;
    }
}
