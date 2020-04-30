package com.example.filehub.service.library.config;

import com.example.filehub.commons.service.constant.RoleConstant;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yinfelix
 * @date 2020/4/30
 */
@Component
public class RequestUrlAccessDecisionManager implements AccessDecisionManager {
    /**
     * 根据请求url所需权限判断当前用户请求是否放行
     *
     * @param authentication   当前用户的安全上下文信息
     * @param object           FilterInvocation对象，储存当前请求信息
     * @param configAttributes 当前请求url所需要的用户权限
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        List<String> urlRoles = configAttributes.stream().map(ConfigAttribute::getAttribute).collect(Collectors.toList());
        if (!urlRoles.contains(RoleConstant.ROLE_GUEST)) {
            /*
            因SSO由前端接管，故此判断暂时失效
             */
            if (authentication instanceof AnonymousAuthenticationToken) {
                throw new AccessDeniedException("请登录!");
            }
        }

        for (String urlRole : urlRoles) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                // 只要用户角色匹配任意一个url权限即可放行
                if (authority.getAuthority().equals(urlRole)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("账户权限不足!");
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
