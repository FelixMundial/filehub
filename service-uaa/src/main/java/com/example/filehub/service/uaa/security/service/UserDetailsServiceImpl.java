package com.example.filehub.service.uaa.security.service;

import com.example.filehub.commons.service.entity.UserAccountInfo;
import com.example.filehub.service.uaa.dao.UserDao;
import com.example.filehub.service.uaa.security.entity.SecurityUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yinfelix
 * @date 2020/4/16
 */
@Service(value = BeanIds.USER_DETAILS_SERVICE)
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccountInfo userAccountInfo = userDao.findByUserLoginName(username);
        if (userAccountInfo != null){
            /*
            需改为传入脱敏字段构造User对象
             */
            return new SecurityUser(userAccountInfo);
        }

//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//
//        if (user != null) {
//            List<TblRole> roles = roleService.selectByUserId(user.getId());
//            roles.forEach(role -> {
//                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getCode());
//                grantedAuthorities.add(simpleGrantedAuthority);
//            });
//
//            ObjectMapper mapper = new ObjectMapper();
//            try {
//                /*
//                将密码外其他用户信息封装进principal字段中
//                 */
//                String principal = mapper.writeValueAsString(user);
//                return new User(principal, user.getPassword(), grantedAuthorities);
//            } catch (JsonProcessingException e) {
//                log.error("", e);
//            }
//        }

        return null;
    }
}
