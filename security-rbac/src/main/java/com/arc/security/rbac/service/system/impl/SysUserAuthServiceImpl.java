package com.arc.security.rbac.service.system.impl;

import com.arc.model.domain.system.SysUserAuth;
import com.arc.security.rbac.service.system.SysUserAuthService;
import com.arc.security.rbac.mapper.system.SysUserAuthMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author 叶超
 * @since 2019/1/30 17:33
 */
@Slf4j
@Service
public class SysUserAuthServiceImpl implements SysUserAuthService {

    @Resource
    private SysUserAuthMapper authMapper;

    @Override
    public int save(SysUserAuth auth) {
        return authMapper.save(auth);
    }

    @Override
    public int delete(Long id) {
        return authMapper.delete(id);
    }

    @Override
    public int update(SysUserAuth auth) {
        return authMapper.update(auth);
    }

    @Override
    public SysUserAuth get(Long id) {
        return authMapper.get(id);
    }

    @Override
    public SysUserAuth getByAndIdentifierIdentityType( String identifier,Integer identityType) {
        return authMapper.getByAndIdentifierIdentityType(identifier,identityType);
    }

}
