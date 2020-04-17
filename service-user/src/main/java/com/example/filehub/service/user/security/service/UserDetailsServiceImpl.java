package com.example.filehub.service.user.security.service;

import com.example.filehub.commons.service.entity.UserAccountInfo;
import com.example.filehub.service.user.dao.UserDao;
import com.example.filehub.service.user.security.entity.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author yinfelix
 * @date 2020/4/16
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccountInfo userAccountInfo = userDao.findByUserLoginName(username);
        if (userAccountInfo == null){
            /*
            若抛出异常则进入Handler处理
             */
            throw new UsernameNotFoundException("用户名不存在！");
        }
        return new SecurityUser(userAccountInfo);
    }
}
