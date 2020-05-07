package com.example.filehub.service.uaa.service;

import com.example.filehub.commons.entity.user.Permission;

/**
 * @author yinfelix
 * @date 2020/4/30
 */
public interface PermissionService {
    Permission getRoleByResourceUrl(String resourceUrl);
}
