package com.example.filehub.service.uaa.controller;

import com.example.filehub.commons.entity.user.Role;
import com.example.filehub.service.uaa.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yinfelix
 * @date 2020/4/30
 */
@Slf4j
@RequestMapping("/api/roles")
@Controller
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping("/query")
    public List<Role> getRolesByResourceUrl(@RequestParam String resourceUrl) {
        log.debug("api: getRolesByResourceUrl(): {}", resourceUrl);
        return roleService.getRolesByResourceUrl(resourceUrl);
    }
}
