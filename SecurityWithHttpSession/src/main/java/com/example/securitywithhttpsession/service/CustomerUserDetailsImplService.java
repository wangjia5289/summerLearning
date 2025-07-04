package com.example.securitywithhttpsession.service;

import com.example.securitywithhttpsession.entity.CustomerUserDetailsImpl;
import com.example.securitywithhttpsession.entity.User;
import com.example.securitywithhttpsession.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsImplService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found" + username);
        }
        return new CustomerUserDetailsImpl(user);
    }
}