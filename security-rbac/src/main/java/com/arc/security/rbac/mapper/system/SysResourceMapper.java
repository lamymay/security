package com.arc.security.rbac.mapper.system;

import com.arc.core.model.domain.system.SysResource;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

/**
 * JAVA项目是分层来写的，
 * 这是持久层，目的是与数据库交互，
 */
public interface SysResourceMapper {

    int save(SysResource user);

    int delete(Long id);

    int update(SysResource user);

    SysResource get(Long id);

    List<SysResource> list();

//    int saveBatch(@Param("resources") List<SysResource> resourceList);

    int saveBatch(@Param("resources") Set<SysResource> resources);

    int deleteIdIn(@NonNull @Param("ids") Set<Long> removeIds);

    int updateBatch(@Param("resources")Set<SysResource> updateResources);

    void truncate(@Param("tableName")String tableName);

//    int saveSysResources(@Param("resources") List<SysResource> resources);
}
