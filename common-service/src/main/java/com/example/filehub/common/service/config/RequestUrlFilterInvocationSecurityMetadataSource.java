package com.example.filehub.common.service.config;

import com.example.filehub.common.service.service.rpc.RoleRpcService;
import com.example.filehub.commons.constant.RoleConstant;
import com.example.filehub.commons.entity.user.Role;
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
        若当前url不对应任何权限，则赋予role_guest权限（生产环境下改为赋予role_admin权限以限制一切未定义访问）
         */
//        return SecurityConfig.createList(RoleConstant.ROLE_ADMIN);
        return SecurityConfig.createList(RoleConstant.ROLE_GUEST);
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
