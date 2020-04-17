package com.example.filehub.service.user.service;

import com.example.filehub.commons.service.entity.UserAccountInfo;

/**
 * @author yinfelix
 * @date 2020/4/14
 */
public interface UserService {
    /**
     *
     * @param user
     * @return UserAuthcInfoVo
     */
    UserAccountInfo saveUser(UserAccountInfo user);

    /**
     *
     * @param userLoginName
     * @param userLoginPassword
     * @return UserAuthcInfoVo
     */
    UserAccountInfo findUserByLoginNameAndLoginPassword(String userLoginName, String userLoginPassword);
}
