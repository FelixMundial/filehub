package com.example.filehub.service.uaa.service;

import com.example.filehub.commons.entity.user.UserAccountInfo;

/**
 * @author yinfelix
 * @date 2020/4/14
 */
public interface UserService {
    /**
     * @param user
     * @return UserAccountInfo
     */
    UserAccountInfo saveUser(UserAccountInfo user);

    /**
     * @param userLoginName
     * @param userLoginPassword
     * @return UserAccountInfo
     */
    UserAccountInfo findUserByLoginNameAndLoginPassword(String userLoginName, String userLoginPassword);

    UserAccountInfo findUserById(Long id);

    UserAccountInfo findUserByLoginName(String userLoginName);
}
