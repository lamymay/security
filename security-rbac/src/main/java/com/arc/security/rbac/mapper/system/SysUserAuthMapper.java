package com.arc.security.rbac.mapper.system;

import com.arc.core.model.domain.system.SysUserAuth;
import org.apache.ibatis.annotations.Param;

/**
 * JAVA项目是分层来写的，
 * 这是持久层，目的是与数据库交互，
 */
public interface SysUserAuthMapper {

    int save(SysUserAuth user);

    int delete(Long id);

    int update(SysUserAuth user);

    SysUserAuth get(Long id);

    /**
     * 通过 登录类型 & 标识 获取用户登录信息
     *
     * @param identifier   标识
     * @param identityType 登录类型
     * @return 用户授权信息
     */
    SysUserAuth getByAndIdentifierIdentityType(@Param("identifier") String identifier, @Param("identityType") Integer identityType);


}
