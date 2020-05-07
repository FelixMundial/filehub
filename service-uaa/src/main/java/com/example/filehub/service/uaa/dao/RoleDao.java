package com.example.filehub.service.uaa.dao;

import com.example.filehub.commons.entity.user.Permission;
import com.example.filehub.commons.entity.user.Role;
import com.example.filehub.commons.entity.user.UserAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yinfelix
 * @date 2020/4/29
 */
public interface RoleDao extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Long> {
    Role findFirstByCode(String code);

    List<Role> findRolesByUsersContaining(UserAccountInfo user);

    List<Role> findRolesByPermissionsContaining(Permission permission);
}
