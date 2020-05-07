package com.example.filehub.common.service.service.rpc;

import com.example.filehub.commons.entity.user.Role;
import com.example.filehub.common.service.service.rpc.impl.RoleRpcFallbackServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yinfelix
 * @date 2020/4/30
 */
@FeignClient(value = "service-uaa", fallback = RoleRpcFallbackServiceImpl.class)
public interface RoleRpcService {
    @GetMapping("/api/roles/query")
    public List<Role> getRolesByResourceUrl(@RequestParam String resourceUrl);
}
