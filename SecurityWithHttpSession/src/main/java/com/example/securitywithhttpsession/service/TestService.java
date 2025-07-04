package com.example.securitywithhttpsession.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @PreAuthorize("hasAnyAuthority('test:test:test')")
    public String test() {
        return "方法执行成功";
    }
}
