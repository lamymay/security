package com.arc.security.rbac.service.system;

import com.arc.core.model.domain.system.SysResource;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

/**
 * 系统资源相关方法
 *
 * @author 叶超
 * @since 2019/1/30 17:33
 */
public interface SysResourceService {

    Long save(SysResource resource);

    int delete(Long id);

    int update(SysResource resource);

    SysResource get(Long id);

    Page<SysResource> page();

    List<SysResource> list();

    /**
     * 加载系统资源通过一些贴在controller上的注解来完成
     * 【资源： URL=Method】 权限控制时候就是对于系统中的接口方法能否允许调用
     *
     * @return
     */
    Integer scanSysResourceByControllerAnnotation();

    int deleteIdIn(Set<Long> ids);
}
