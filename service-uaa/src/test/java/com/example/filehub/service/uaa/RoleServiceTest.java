package com.example.filehub.service.uaa;

import com.example.filehub.commons.service.entity.user.Role;
import com.example.filehub.commons.service.entity.user.UserAccountInfo;
import com.example.filehub.commons.service.user.dao.RoleDao;
import com.example.filehub.commons.service.user.dao.UserDao;
import com.example.filehub.commons.service.user.service.RoleService;
import com.example.filehub.commons.service.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    BCryptPasswordEncoder passwordEncoder;

    @Test
//    @Rollback(value = false)
    void manyToManyTest() {
        LocalDateTime now = LocalDateTime.now();
        UserAccountInfo userAccountInfo = new UserAccountInfo("admin001", passwordEncoder.encode("root"), now, now);
//        Role role = new Role("library_test", null, now.plusDays(2));
        Role role = roleDao.findFirstByCode("admin");

        userAccountInfo.getRoles().add(role);
        role.getUsers().add(userAccountInfo);

        userDao.save(userAccountInfo);
    }
}