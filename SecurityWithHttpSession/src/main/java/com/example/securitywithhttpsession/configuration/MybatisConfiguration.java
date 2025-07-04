package com.example.securitywithhttpsession.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.securitywithhttpsession.mapper")
public class MybatisConfiguration {
}
