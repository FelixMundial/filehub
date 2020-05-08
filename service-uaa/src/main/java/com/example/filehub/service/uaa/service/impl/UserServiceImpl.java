package com.example.filehub.service.uaa.service.impl;

import com.example.filehub.commons.entity.user.Role;
import com.example.filehub.commons.entity.user.UserAccountInfo;
import com.example.filehub.service.uaa.dao.UserDao;
import com.example.filehub.service.uaa.service.RoleService;
import com.example.filehub.service.uaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author yinfelix
 * @date 2020/4/14
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserAccountInfo saveUser(UserAccountInfo user) {
        @NotNull(message = "登录账户名不可为空") final String userLoginName = user.getUserLoginName();
        @NotNull(message = "登录密码不可为空") final String userLoginPassword = user.getUserLoginPassword();
        if (!StringUtils.isEmpty(userLoginName) && !StringUtils.isEmpty(userLoginPassword)) {
            final LocalDateTime creationTime = LocalDateTime.now();
            /*
            使用BCryptPasswordEncoder对用户密码进行加密存储
             */
            String encodedPassword = passwordEncoder.encode(userLoginPassword);
            user = new UserAccountInfo(userLoginName, encodedPassword, creationTime, creationTime);

            /*
            向新注册用户添加 USER 权限
             */
            Role userRole = roleService.getRoleByRoleId(2L);
            userRole.getUsers().add(user);
            user.getRoles().add(userRole);

            return userDao.saveAndFlush(user);
        }
        return null;
    }

    /**
     * @deprecated
     */
    @Transactional(readOnly = true)
    @Override
    public UserAccountInfo findUserByLoginNameAndLoginPassword(String userLoginName, String userLoginPassword) {
        return userDao.findByUserLoginNameAndUserLoginPassword(userLoginName, userLoginPassword);
    }

    @Override
    public UserAccountInfo findUserById(Long id) {
        final UserAccountInfo user = userDao.findById(id).orElse(null);
        if (user != null) {
            user.setUserLoginPassword("");
        }
        return user;
    }

    @Override
    public UserAccountInfo findUserByLoginName(String userLoginName) {
        return userDao.findByUserLoginName(userLoginName);
    }
}
