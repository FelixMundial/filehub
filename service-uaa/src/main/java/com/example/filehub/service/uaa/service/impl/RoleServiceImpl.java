package com.example.filehub.service.uaa.service.impl;

import com.example.filehub.commons.entity.user.Permission;
import com.example.filehub.commons.entity.user.Role;
import com.example.filehub.commons.entity.user.UserAccountInfo;
import com.example.filehub.service.uaa.dao.PermissionDao;
import com.example.filehub.service.uaa.dao.RoleDao;
import com.example.filehub.service.uaa.dao.UserDao;
import com.example.filehub.service.uaa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yinfelix
 * @date 2020/4/29
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PermissionDao permissionDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Role saveRole(Role role) {
        return roleDao.saveAndFlush(role);
    }

    @Override
    public Role getRoleByRoleId(Long roleId) {
        return roleDao.getOne(roleId);
    }

    @Override
    public List<Role> getRolesByUserId(Long uid) {
        UserAccountInfo userAccountInfo = userDao.getOne(uid);
        return roleDao.findRolesByUsersContaining(userAccountInfo);
    }

    @Override
    public List<Role> getRolesByResourceUrl(String resourceUrl) {
        Permission permission = permissionDao.findByResourceUrl(resourceUrl);
        return roleDao.findRolesByPermissionsContaining(permission);
    }


}
