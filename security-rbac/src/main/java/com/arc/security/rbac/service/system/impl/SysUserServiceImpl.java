package com.arc.security.rbac.service.system.impl;

import com.arc.core.model.domain.system.SysUser;
import com.arc.core.model.domain.system.SysUserAuth;
import com.arc.security.rbac.service.system.SysUserService;
import com.arc.security.rbac.mapper.system.SysUserAuthMapper;
import com.arc.security.rbac.mapper.system.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 叶超
 * @since 2018/12/26 11:28
 */
@Slf4j
@Transactional
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper userMapper;

    /**
     * 登陆校验真正在该该表
     */
    @Resource
    private SysUserAuthMapper authMapper;


    @Override

    public Long save(SysUser user) {
        return userMapper.save(user) == 1 ? user.getId() : null;
    }

    @Override
    public int delete(Long id) {
        return userMapper.delete(id);
    }

    @Override
    public int update(SysUser user) {
//        return sysUserMapper.update(user);
        Integer update = userMapper.update(user);
        log.debug("#############################");
        log.debug("结果={}", update);
        log.debug("结果={}", update);
        log.debug("#############################");
        return update;
    }

    //测试
    //    @Transactional(rollbackFor = Exception.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public int updateBatch(List<SysUser> users) {
        return 0;
    }

    @Override
    public SysUser get(Long id) {
        return userMapper.get(id);
    }


    @Override
    public List<SysUser> list() {
        return userMapper.list();
    }

    @Override
    public SysUser getUserByIdentityTypeAndIdentifier(Integer identityType, String identifier) {
        //不指定类型时候请使用何种方案？ 方案1 采用账号登陆， 方案2 尝试各种登陆方式
        //方案1 约定，在sql中完成嘛?? 登陆时候默认用第一种种类型校验 identityType=1，即username、password
        log.debug("方案1 约定，在sql中完成嘛?? 登陆时候默认用第一种种类型校验 identityType=1，即username、password .传入identityType={}", identityType);
        identityType = 1;
        SysUser user = userMapper.getUserByIdentityTypeAndIdentifier(identityType, identifier);

        log.info("   访问数据库方法未完成  return userMapper.getUserByAndIdentifierIdentityType(identifier, identityType);\n {}", LocalDateTime.now());
        return user;

    }


    /**
     * 0根据用户名（用户唯一标识），获取用户数据
     * 1若用户数据可查找到，判断秘密是否匹配，不匹配的返回失败
     * 2若用户不存在，直接返回失败
     *
     * @param identifier
     * @return
     */
    @Override
    public SysUser loadUserByUsername(String identifier) {
        return getUserByIdentityTypeAndIdentifier(1, identifier);
    }

    @Override
    public SysUser login(String username, String password) {
        SysUser sysUser = loadUserByUsername(username);
        if (sysUser != null && sysUser.getAuth().getCredential().endsWith(password)) {
            return sysUser;
        }
        return null;
    }

    //登陆
    @Override
    public SysUser login(String u, String p, Integer identityType) {
        SysUserAuth auth = authMapper.getByAndIdentifierIdentityType(u, identityType);
        if (null == auth) {
            return null;
        }
        SysUser sysUser = userMapper.get(auth.getUserId());
        return sysUser;
    }
}
