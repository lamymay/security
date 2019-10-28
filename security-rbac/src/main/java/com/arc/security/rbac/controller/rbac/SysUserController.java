package com.arc.security.rbac.controller.rbac;

import com.arc.core.model.domain.system.SysUser;
import com.arc.core.model.request.system.IdLongRequest;
import com.arc.core.model.request.system.UserRequest;
import com.arc.core.model.vo.ResponseVo;
import com.arc.security.rbac.service.system.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户相关API
 *
 * @author 叶超
 * @since 2018/12/25
 */
@Slf4j
@Controller
@RestController
@RequestMapping("/sys/users")
public class SysUserController {

    @Resource
    private SysUserService userService;


    /**
     * 新建用户
     * 注意
     * 1请求类型为Content-Type:application/json
     * 2方法： POST
     *
     * @param user SysUser
     * @return ResponseVo
     */
    @PostMapping(value = "/save")
    public ResponseVo save(@RequestBody SysUser user) {
        log.debug("新建用户，参数 user={}, ", user.toString());
        return ResponseVo.success(userService.save(user));
    }


    /**
     * 删除
     *
     * @param id IdLongRequest id
     * @return ResponseVo
     */
    @PostMapping(value = "/delete")
    public ResponseVo delete(@RequestBody IdLongRequest id) {
        log.debug("参数删除用户，参数id={}", id);
        return ResponseVo.success(userService.delete(id.getId()));

    }


    /**
     * 更新用户
     * 注意 1请求类型为Content-Type:application/json
     * 对于必要参数没有传则判断了一下会返回错误代码
     *
     * @return ResponseVo
     */
    @PostMapping("/update")
    public ResponseVo update(@RequestBody SysUser user) {
        log.debug("更新用户,参数user={}, ", user.toString());
        return ResponseVo.success(userService.update(user));
    }

    /**
     * 获取单个用户
     *
     * @param id IdLongRequest
     * @return ResponseVo
     */
    @PostMapping(value = "/get")
    public ResponseVo get(@RequestBody IdLongRequest id) {
        log.debug("获取单个用户,参数 id={}", id);
        return ResponseVo.success(userService.get(id.getId()));
    }

    /**
     * 获取用户列表
     *
     * @return ResponseVo
     */
    @PostMapping(value = "/page")
    public ResponseVo list(@RequestBody UserRequest user) {
        log.debug("获取用户列表，参数={}", user);
        return ResponseVo.success(userService.list());
    }


}

