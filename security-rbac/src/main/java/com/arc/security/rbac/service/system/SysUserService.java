package com.arc.security.rbac.service.system;


import com.arc.model.domain.system.SysUser;

import java.util.List;

/**
 * JAVA项目是分层来写的，
 * 这是服务层，目的是处理业务，
 *
 * @author yechao
 * @date 2018/12/21
 */
public interface SysUserService {

    Long save(SysUser user);

    int delete(Long id);

    int update(SysUser user);

    int updateBatch(List<SysUser> users);

    SysUser get(Long id);

    List<SysUser> list();


    /**
     * 特殊方法  根据用户名称获取用户信息
     * t_sys_user_auth left join  t_sys_user
     *
     * @param identityType 登录类型
     * @param identifier   身份标记
     * @return SysUSer with auth
     */
    SysUser getUserByIdentityTypeAndIdentifier(Integer identityType, String identifier);

    //UserDetailsService
    SysUser loadUserByUsername(String username);


    //for test
    SysUser login(String username, String password);

    SysUser login(String u, String p, Integer identityType);
}
