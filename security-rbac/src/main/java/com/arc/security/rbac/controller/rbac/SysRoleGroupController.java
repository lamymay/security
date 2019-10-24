package com.arc.security.rbac.controller.rbac;

import com.arc.model.domain.system.SysRole;
import com.arc.model.request.system.SysRoleRequest;
import com.arc.model.vo.ResponseVo;
import com.arc.security.rbac.service.system.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统角色相关接口
 * 接口提供的功能：
 * 单表：查询、新增、批量新增、删除、批量删除、更新、批量更新、分页查询
 *
 * @author 叶超
 * @since: 2019/4/27
 */
@Slf4j
@RestController
@RequestMapping("/sys/role/groups")
public class SysRoleGroupController {

    @Resource
    private SysRoleService roleService;

    /**
     * 新建角色
     * 注意
     * 1请求类型为Content-Type:application/json
     * 2方法： POST
     *
     * @param role
     * @return
     */
    @PostMapping(value = "")
    public ResponseVo save(@RequestBody SysRole role) {
        log.debug("新建角色，参数 sysLog={}, ", role.toString());
        return ResponseVo.success(roleService.save(role));
    }

    /**
     * 批量操作--save
     *
     * @param roles
     * @return
     */
    @PostMapping("/save/batch")
    public ResponseVo batchSave(@RequestBody List<SysRole> roles) {
        log.debug("batchSave 角色,参数 roles={}, ", roles.size());
        return ResponseVo.success(roleService.batchSave(roles));
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        log.debug("参数删除角色，参数id={}", id);
        return ResponseVo.success(roleService.delete(id));

    }

    /**
     * 更新角色
     * 注意 1请求类型为Content-Type:application/json
     * 方法： PUT
     * 对于必要参数没有传则判断了一下会返回错误代码
     * http://ip:port/url
     *
     * @param role
     * @return ResponseVo
     */
    @PutMapping(value = "/{id}")
    public ResponseVo update(@PathVariable Long id, @RequestBody SysRole role) {
        log.debug("role是null吗{}", role == null);
        if (role.getId() == null || !role.getId().equals(id)) {
            role.setId(id);
        }
        log.debug("更新角色,参数 roles={}, ", role.toString());
        return ResponseVo.success(roleService.update(role));
    }

    /**
     * 批量操作--更新
     *
     * @param roles
     * @return
     */
    @PutMapping("/update/batch")
    public ResponseVo batchUpdateWithResources(@RequestBody List<SysRole> roles) {
        log.debug("更新角色,参数 roles={}, ", roles.size());
        return ResponseVo.success(roleService.updateBatch(roles));
    }

    /**
     * 获取单个角色
     * ApiImplicitParam这个注解不只是注解，还会影响运行期的程序，例子如下：
     *
     * @param id 主键
     * @return ResponseVo
     */
//    @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", dataType = "long", required = true)})
    @GetMapping(value = "/{id}")
    public ResponseVo get(@PathVariable("id") Long id) {
        log.debug("获取单个角色,参数 id={}", id);
        return ResponseVo.success(roleService.get(id));
    }

    /**
     * 获取角色列表
     *
     * @return ResponseVo
     */
    @PostMapping(value = "/page")
    public ResponseVo page(@RequestBody SysRoleRequest roleRequest) {
        log.debug("获取角色列表，参数={}", roleRequest);
        return ResponseVo.success(roleService.list());
    }

    //操作子资源的接口
    /**
     * 新增角色的同时添加资源
     *
     * @param id
     * @param role
     * @return
     */
    @PostMapping(value = "/{id}/resources")
    public ResponseVo<Integer> saveRoleWithResources(@PathVariable("id") Long id, @RequestBody SysRoleRequest role) {
        role.setId(id);
        log.debug("新增角色的同时添加资源,参数 ={}", role);
        return ResponseVo.success(roleService.updateWithResources(role));
    }

    /**
     * 向角色新增资源
     *
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}/resources")
    public ResponseVo<Integer> addResourcesToRole(@PathVariable("id") Long id, @RequestBody SysRoleRequest role) {
        role.setId(id);
        log.debug("向角色新增资源,参数 ={}", role);
        return ResponseVo.success(roleService.updateWithResources(role));
    }


}

