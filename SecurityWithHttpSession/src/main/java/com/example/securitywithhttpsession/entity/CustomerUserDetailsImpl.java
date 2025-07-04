package com.example.securitywithhttpsession.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomerUserDetailsImpl implements UserDetails {

    private final User user;

    public CustomerUserDetailsImpl(User user) {
        this.user = user;
    }

    // 必须实现的 3 个方法
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 可选择实现的 4 个方法
    @Override
    public boolean isAccountNonExpired() {
        return user.getIsAccountnonexpired() != null && user.getIsAccountnonexpired() != 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsAccountnonlocked() != null && user.getIsAccountnonlocked() !=0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsCredentialsnonexpired() != null && user.getIsCredentialsnonexpired() != 0;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnabled() != null && user.getIsEnabled() != 0;
    }

    // 选择性扩展的字段
    public String getEmail() {
        return user.getEmail();
    }

    public String getPhoneNumber() {
        return user.getPhoneNumber();
    }
}