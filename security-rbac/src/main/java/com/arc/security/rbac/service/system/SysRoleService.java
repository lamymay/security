package com.arc.security.rbac.service.system;

import com.arc.core.model.domain.system.SysRole;
import com.arc.core.model.request.system.SysRoleRequest;

import java.util.List;


/**
 * ${meta.tableComment}服务
 *
 * @author 叶超
 * @since 2019/1/30 17:33
 */
public interface SysRoleService {

    /**
     * 保存一条数据并返回数据的主键
     *
     * @param role 实体
     * @return 主键 PK
     */
    Long save(SysRole role);

    /**
     * 通过主键删除一条数据
     *
     * @param id 主键
     * @return 影响条数
     */
    int delete(Long id);

    /**
     * 更新一条数据
     *
     * @param role
     * @return
     */
    int update(SysRole role);

    /**
     * 通过主键查询一条数据
     *
     * @param id 主键
     * @return 返回一条数据
     */
    SysRole get(Long id);

    /**
     * 无条件查询全部数据
     *
     * @return 全部数据
     */
    List<SysRole> list();

    /**
     * 条件查询数据列表
     *
     * @return 数据集合
     */
    List<SysRole> listBySysRoleRequest(SysRoleRequest roleRequest);

    /**
     * 分页条件查询数据列表
     *
     * @param roleRequest SysRoleRequest
     * @return 数据集合
     */
    List<SysRole> listPage(SysRoleRequest roleRequest);

    /**
     * 批量插入
     *
     * @param roles 数据集合
     * @return 影响条数
     */
    Integer batchSave(List<SysRole> roles);

    /**
     * 批量更新
     *
     * @param roles 数据集合
     * @return 影响条数
     */
    Integer updateBatch(List<SysRole> roles);

    /**
     * 向角色新增资源
     *
     * @param role 一条带子资源的数据
     * @return 影响条数
     */
    int updateWithResources(SysRoleRequest role);
}
