package com.arc.security.rbac.mapper.system;

import com.arc.model.domain.system.RoleResource;
import com.arc.model.domain.system.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: yechao
 * @date: 2019/1/24 9:57
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    int save(SysRole user);

    int delete(Long id);

    int update(SysRole user);

    SysRole get(Long id);

    List<SysRole> list();

    int saveBatch(@Param("roles") List<SysRole> roles);


    // 中间表数据维护，即：表 t_sys_role_resource
    /**
     * 记录关系表数据
     *
     * @param id          角色id
     * @param resourceIds 资源ids
     * @return 操作影响行数
     */
    int saveRoleResource(Long id, List<Long> resourceIds);

    /**
     * 删除全部的角色与资源的关系
     *
     * @param id
     */
    int deleteAllRoleResource(Long id);

    /**
     * 关系表批量保存
     *
     * @param roleResources
     * @return
     */
    int saveBatchRoleResource(@Param("roleResources")    List<RoleResource> roleResources);
}
