package com.example.filehub.service.uaa;

import com.example.filehub.commons.service.constant.RoleConstant;
import com.example.filehub.commons.service.entity.user.Permission;
import com.example.filehub.commons.service.entity.user.Role;
import com.example.filehub.commons.service.entity.user.UserAccountInfo;
import com.example.filehub.service.uaa.dao.PermissionDao;
import com.example.filehub.service.uaa.dao.RoleDao;
import com.example.filehub.service.uaa.dao.UserDao;
import com.example.filehub.service.uaa.service.RoleService;
import com.example.filehub.service.uaa.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Transactional
@SpringBootTest
class RoleServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @Test
    void getRolesByUserId() {
        Long uid = 50L;
        UserAccountInfo user = userService.findUserById(uid);
        roleService.getRolesByUserId(uid).forEach(role -> System.out.println(user.getUserLoginName() + ": " + role.getCode()));
    }

    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    PermissionDao permissionDao;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
//    @Rollback(value = false)
    void saveUserWithRoleTest() {
        LocalDateTime now = LocalDateTime.now();
        UserAccountInfo userAccountInfo = new UserAccountInfo("admin", passwordEncoder.encode("root"), now, now);
//        Role role = new Role("library_test", null, now.plusDays(2));
        Role role = roleDao.findFirstByCode(RoleConstant.ROLE_ADMIN);

        userAccountInfo.getRoles().add(role);
        role.getUsers().add(userAccountInfo);

        userDao.save(userAccountInfo);
    }

    @Test
//    @Rollback(value = false)
    void savePermissionWithRoleTest() {
        LocalDateTime now = LocalDateTime.now();
        Permission permission = new Permission("upload_files", "/library/files/upload", now);
        Role roleLibraryOwner = roleDao.findFirstByCode(RoleConstant.ROLE_LIBRARY_OWNER);
        Role roleLibraryCollaborator = roleDao.findFirstByCode(RoleConstant.ROLE_LIBRARY_COLLABORATOR);
//        Role roleAdmin = roleDao.findFirstByCode(RoleConstant.ROLE_ADMIN);

        Set<Role> permissionRoles = permission.getRoles();
        permissionRoles.add(roleLibraryOwner);
        permissionRoles.add(roleLibraryCollaborator);
//        permissionRoles.add(roleAdmin);

        roleLibraryOwner.getPermissions().add(permission);
        roleLibraryCollaborator.getPermissions().add(permission);
//        roleAdmin.getPermissions().add(permission);

        permissionDao.save(permission);
    }
}