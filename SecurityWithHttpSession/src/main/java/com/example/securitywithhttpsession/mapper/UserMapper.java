package com.example.securitywithhttpsession.mapper;

import com.example.securitywithhttpsession.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
* @author ASUS
* @description 针对表【users】的数据库操作Mapper
* @createDate 2025-07-04 11:05:37
* @Entity com.example.securitywithhttpsession.entity.User
*/
public interface UserMapper {

    User getUserByUserName(@Param("username") String username);

    int insertUser(@Param("username") String username,
                   @Param("password") String password,
                   @Param("email") String email,
                   @Param("phonenumber") String phonenumber);


}




