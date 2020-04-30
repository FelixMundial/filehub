package com.example.filehub.commons.service.user.dao;

import com.example.filehub.commons.service.entity.user.UserAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author yinfelix
 * @date 2020/4/14
 */
@Repository
public interface UserDao extends JpaRepository<UserAccountInfo, Long>, JpaSpecificationExecutor<UserAccountInfo> {

    UserAccountInfo findByUserLoginNameAndUserLoginPassword(String userLoginName, String userLoginPassword);

    UserAccountInfo findByUserLoginName(String userLoginName);
}
