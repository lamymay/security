package com.arc.security.rbac.service.system.impl;

import com.arc.core.model.domain.system.SysLog;
import com.arc.security.rbac.mapper.system.SysLogMapper;
import com.arc.security.rbac.service.system.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 叶超
 * @since 2019/2/2 11:42
 */
@Slf4j
@Service("sysLogServiceImpl")
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper logMapper;

    @Override
    public Long save(SysLog sysLog) {
        return logMapper.save(sysLog) == 1 ? sysLog.getId() : null;
    }

    @Override
    public Integer delete(Long id) {
        return logMapper.delete(id);
    }

    @Override
    public Integer update(SysLog sysLog) {
        return logMapper.update(sysLog);
    }

    @Override
    public SysLog get(Long id) {
        return logMapper.get(id);
    }

    @Override
    public List<SysLog> page() {
        return logMapper.list();
    }
}
