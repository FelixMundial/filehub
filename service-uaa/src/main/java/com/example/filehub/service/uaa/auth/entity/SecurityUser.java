package com.example.filehub.service.uaa.auth.entity;

import com.example.filehub.commons.entity.user.Role;
import com.example.filehub.commons.entity.user.UserAccountInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author yinfelix
 * @date 2020/4/16
 */
@Slf4j
@Data
public class SecurityUser implements UserDetails {
    /**
     * 当前登录用户
     */
    private transient UserAccountInfo currentUser;

    public SecurityUser() {
    }

    public SecurityUser(UserAccountInfo user) {
        this.currentUser = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.currentUser.getRoles().stream()
                .map(Role::getCode)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String getPassword() {
        return currentUser.getUserLoginPassword();
    }

    @Override
    public String getUsername() {
        return currentUser.getUserLoginName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
