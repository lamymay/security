package com.arc.security.rbac.mapper.system;

import com.arc.core.model.domain.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * JAVA项目是分层来写的，
 * 这是持久层，目的是与数据库交互，
 *
 * @author X
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    int save(SysUser user);

    int delete(Long id);

    int update(SysUser user);

    SysUser get(Long id);

    /**
     * 根据用户id获取用户的
     *
     * @param id 根据用户id获取用户的
     * @return SysUser
     */
    SysUser getUserWithAuth(Long id);


    /**
     * 根据类型与账号获取用户
     *
     * @param identityType Integer
     * @param identifier   String
     * @return SysUser
     */
    SysUser getUserByIdentityTypeAndIdentifier(@Param("identityType") Integer identityType, @Param("identifier") String identifier);

    List<SysUser> list();


//    SysUser getUserByIdentifierAndIdentityType(@Param("identifier") String identifier, @Param("identityType") Integer identityType);
}
