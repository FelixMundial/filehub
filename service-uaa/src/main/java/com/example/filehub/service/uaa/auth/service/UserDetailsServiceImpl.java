package com.example.filehub.service.uaa.auth.service;

import com.example.filehub.commons.entity.user.UserAccountInfo;
import com.example.filehub.service.uaa.auth.entity.SecurityUser;
import com.example.filehub.service.uaa.service.RoleService;
import com.example.filehub.service.uaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author yinfelix
 * @date 2020/4/16
 */
@Service(value = BeanIds.USER_DETAILS_SERVICE)
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccountInfo userAccountInfo = userService.findUserByLoginName(username);
        /*
        构造UserDetails实现类
         */
        return new SecurityUser(userAccountInfo);
    }
}
