package com.example.filehub.service.user.service.impl;

import com.example.filehub.service.user.dao.UserDao;
import com.example.filehub.commons.service.entity.UserAccountInfo;
import com.example.filehub.service.user.service.UserService;
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
    UserDao userDao;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

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
}
