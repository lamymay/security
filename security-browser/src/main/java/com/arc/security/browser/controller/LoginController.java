package com.arc.security.browser.controller;

import com.arc.core.model.request.system.UserRequest;
import com.arc.core.model.vo.ResponseVo;
import com.arc.security.rbac.service.system.SysUserService;
import com.arc.utils.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

/**
 * @author 叶超
 * @since 2019/10/28 13:16
 */
public class LoginController {
    @Resource
    private SysUserService userService;

    // 测试登陆  todo 待删除
    @PostMapping("/login")
    public ResponseVo login(@RequestBody UserRequest user) {
        return ResponseVo.failure(userService.login(user.getIdentifier(), user.getCredential()));
    }

    // 测试登陆  todo 待删除
    @GetMapping("/login/{u}/{p}/{identityType}")
    public ResponseVo test1(@PathVariable String u, @PathVariable String p, @PathVariable Integer identityType) {
        Assert.notNull(u, "username 不能为空");
        Assert.notNull(p, "password 不能为空");
        Assert.notNull(identityType, "认证类型不能为空");
        return ResponseVo.success(userService.login(u, p, identityType));
    }

}
