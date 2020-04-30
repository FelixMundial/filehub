package com.example.filehub.service.library.service.rpc;

import com.example.filehub.commons.service.entity.user.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yinfelix
 * @date 2020/4/30
 */
@FeignClient(value = "service-uaa")
public interface RoleRpcService {
    @GetMapping("/api/roles/query")
    public List<Role> getRolesByResourceUrl(@RequestParam String resourceUrl);
}
