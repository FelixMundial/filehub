package com.example.filehub.service.uaa.service;

import com.example.filehub.commons.entity.user.Role;

import java.util.List;

/**
 * @author yinfelix
 * @date 2020/4/29
 */
public interface RoleService {
    Role saveRole(Role role);

    Role getRoleByRoleId(Long roleId);

    List<Role> getRolesByUserId(Long uid);

    List<Role> getRolesByResourceUrl(String resourceUrl);
}
