package com.example.filehub.service.uaa.service.impl;

import com.example.filehub.commons.entity.user.Permission;
import com.example.filehub.service.uaa.dao.PermissionDao;
import com.example.filehub.service.uaa.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yinfelix
 * @date 2020/4/30
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Permission getRoleByResourceUrl(String resourceUrl) {
        return permissionDao.findByResourceUrl(resourceUrl);
    }
}
