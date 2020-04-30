package com.example.filehub.service.library.service.rpc.impl;

import com.example.filehub.commons.service.entity.user.Role;
import com.example.filehub.service.library.service.rpc.RoleRpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yinfelix
 * @date 2020/4/30
 */
@Slf4j
@Component
public class RoleRpcFallbackServiceImpl implements RoleRpcService {
    @Override
    public List<Role> getRolesByResourceUrl(String resourceUrl) {
        log.warn("调用UAA权限服务发生熔断!");
        return null;
    }
}
