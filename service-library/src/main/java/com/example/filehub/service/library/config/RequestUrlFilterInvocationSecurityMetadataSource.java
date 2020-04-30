package com.example.filehub.service.library.config;

import com.example.filehub.commons.service.constant.RoleConstant;
import com.example.filehub.commons.service.entity.user.Role;
import com.example.filehub.service.library.service.rpc.RoleRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author yinfelix
 * @date 2020/4/30
 */
@Component
public class RequestUrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    /*
    需通过rpc调用Service
     */
    @Autowired
    private RoleRpcService roleRpcService;

    /**
     * 获取请求url所需要的用户权限
     *
     * @param object FilterInvocation对象，储存当前请求信息
     * @return 请求url所需要的用户权限（null：不需要任何权限）
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Role> roles = roleRpcService.getRolesByResourceUrl(requestUrl);
        if (roles != null) {
            return SecurityConfig.createList((String[]) roles.stream().map(Role::getCode).toArray());
        }
        /*
        当前url不对应任何权限，直接禁止用户访问
         */
        return SecurityConfig.createList(RoleConstant.ROLE_ADMIN);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
