package com.example.securitywithhttpsession.mapper;

import com.example.securitywithhttpsession.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
* @author ASUS
* @description 针对表【user_role】的数据库操作Mapper
* @createDate 2025-07-04 12:02:28
* @Entity com.example.securitywithhttpsession.entity.UserRole
*/
public interface UserRoleMapper {
    int insertUserRole(@Param("userid") int userid,
                       @Param("roleid") int roleid);

}




