package com.example.filehub.service.library.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import java.util.Collection;

/**
 * @author yinfelix
 * @date 2020/4/30
 */
public class RequestUrlAccessDecisionManager implements AccessDecisionManager {
    /**
     * 根据请求url所需权限判断当前请求是否放行
     *
     * @param authentication   当前用户的安全上下文信息
     * @param object           FilterInvocation对象，储存当前请求信息
     * @param configAttributes 当前请求url所需要的用户权限
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
