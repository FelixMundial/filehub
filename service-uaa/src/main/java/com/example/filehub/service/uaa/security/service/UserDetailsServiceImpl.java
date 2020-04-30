package com.example.filehub.service.uaa.security.service;

import com.example.filehub.commons.service.entity.user.Role;
import com.example.filehub.commons.service.entity.user.UserAccountInfo;
import com.example.filehub.service.uaa.security.entity.SecurityUser;
import com.example.filehub.service.uaa.service.RoleService;
import com.example.filehub.service.uaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinfelix
 * @date 2020/4/16
 */
@Service(value = BeanIds.USER_DETAILS_SERVICE)
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccountInfo userAccountInfo = userService.findUserByLoginName(username);
        List<Role> rolesByUserId = roleService.getRolesByUserId(userAccountInfo.getUserId());
        /*
        构造UserDetails实现类
         */
        return new SecurityUser(userAccountInfo);
    }
}
