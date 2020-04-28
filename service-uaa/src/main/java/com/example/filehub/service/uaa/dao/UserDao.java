package com.example.filehub.service.uaa.dao;

import com.example.filehub.commons.service.entity.UserAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author yinfelix
 * @date 2020/4/14
 */
public interface UserDao extends JpaRepository<UserAccountInfo, Long>, JpaSpecificationExecutor<UserAccountInfo> {

    UserAccountInfo findByUserLoginNameAndUserLoginPassword(String userLoginName, String userLoginPassword);

    UserAccountInfo findByUserLoginName(String userLoginName);
}
