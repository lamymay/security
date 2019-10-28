package com.arc.security.rbac.controller.rbac;

import com.arc.core.model.domain.system.SysResource;
import com.arc.core.model.request.system.IdLongRequest;
import com.arc.core.model.vo.ResponseVo;
import com.arc.security.rbac.service.system.SysResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 系统资源相关接口
 *
 * @author 叶超
 * @since 2019/4/27 17:02
 */
@Slf4j
@RestController
@RequestMapping("/sys/resources")
public class SysResourceController {

    @Resource
    private SysResourceService resourceService;

    @PostMapping(value = "/save")
    public ResponseVo save(@RequestBody SysResource resource) {
        log.debug("新建角色，参数 sysLog={}, ", resource.toString());
        return ResponseVo.success(resourceService.save(resource));
    }

    @PostMapping(value = "/delete")
    public ResponseVo delete(@PathVariable Long id) {
        log.debug("参数删除角色，参数id={}", id);
        return ResponseVo.success(resourceService.delete(id));
    }

    @PostMapping(value = "/delete/batch")
    public ResponseVo delete(@RequestBody Set<Long> ids) {
        log.debug("参数删除角色，参数id={}", ids);
        return ResponseVo.success(resourceService.deleteIdIn(ids));
    }

    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody SysResource sysLog) {
        log.debug("更新角色,参数sysLog={}, ", sysLog.toString());
        return ResponseVo.success(resourceService.update(sysLog));
    }

    @PostMapping(value = "/get")
    public ResponseVo get(@RequestBody IdLongRequest id) {
        log.debug("获取单个角色,参数 id={}", id);
        return ResponseVo.success(resourceService.get(id.getId()));
    }

    @PostMapping(value = "/page")
    public ResponseVo list() {
        log.debug("获取角色列表，无参数！");
        return ResponseVo.success(resourceService.page());
    }

    @RequestMapping("/scan")
    public ResponseVo scanSysResourceByControllerAnnotation() {
        return ResponseVo.success(resourceService.scanSysResourceByControllerAnnotation());
    }
}
