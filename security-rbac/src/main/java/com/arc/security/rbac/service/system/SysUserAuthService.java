package com.arc.security.rbac.service.system;

import com.arc.model.domain.system.SysUserAuth;


/**
 * @author 叶超
 * @since 2019/1/30 17:33
 */
public interface SysUserAuthService {

    int save(SysUserAuth auth);

    int delete(Long id);

    int update(SysUserAuth auth);

    SysUserAuth get(Long id);

    /**
     * 通过 登录类型 & 标识 获取用户登录信息
     * @param identityType 登录类型
     * @param identifier 标识
     * @return 用户授权信息
     */
    SysUserAuth getByAndIdentifierIdentityType(String identifier,Integer identityType);

}
