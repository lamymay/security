package com.arc.security.rbac.controller.rbac;

import com.arc.model.domain.system.SysResource;
import com.arc.model.vo.ResponseVo;
import com.arc.security.rbac.service.system.SysResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 系统资源相关接口
 *
 * @author 叶超
 * @since: 2019/4/27 17:02
 */
@Slf4j
@RestController
@RequestMapping("/sys/resources")
public class SysResourceController {

    @Autowired
    private SysResourceService resourceService;

    @PostMapping(value = "")
    public ResponseVo save(@RequestBody SysResource resource) {
        log.debug("新建角色，参数 sysLog={}, ", resource.toString());
        return ResponseVo.success(resourceService.save(resource));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        log.debug("参数删除角色，参数id={}", id);
        return ResponseVo.success(resourceService.delete(id));
    }

    @PostMapping(value = "/delete/batch")
    public ResponseVo delete(@RequestBody Set<Long> ids) {
        log.debug("参数删除角色，参数id={}", ids);
        return ResponseVo.success(resourceService.deleteIdIn(ids));
    }

    @PutMapping("")
    public ResponseVo update(@RequestBody SysResource sysLog) {
        log.debug("更新角色,参数sysLog={}, ", sysLog.toString());
        return ResponseVo.success(resourceService.update(sysLog));
    }

    @GetMapping(value = "/{id}")
    public ResponseVo get(@PathVariable("id") Long id) {
        log.debug("获取单个角色,参数 id={}", id);
        SysResource sysLog = resourceService.get(id);
        ResponseVo<SysResource> success = ResponseVo.success(sysLog);
        log.debug("------------------------------------");
        log.debug("get结果={}", success);
        log.debug("------------------------------------");
        return success;
    }

    @PostMapping(value = "/page")
    public ResponseVo list() {
        log.debug("获取角色列表，无参数！");
        return ResponseVo.success(resourceService.page());
    }

    @GetMapping("/scan")
    public ResponseVo scanSysResourceByControllerAnnotation() {
        return ResponseVo.success(resourceService.scanSysResourceByControllerAnnotation());
    }
}
