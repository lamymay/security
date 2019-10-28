package com.arc.security.rbac.controller.rbac;

import com.arc.core.model.request.system.UserRequest;
import com.arc.core.model.vo.ResponseVo;
import com.arc.security.rbac.service.system.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lamy
 * @since 2019/10/24 22:45
 */
@Slf4j
@Controller
@RestController
@RequestMapping("/sys/login")
public class TestLoginController {


    @Resource
    private SysUserService userService;


    //   todo 待删除 测试登陆
    @PostMapping("/1")
    public ResponseVo login(@RequestBody UserRequest user) {
        return ResponseVo.failure(userService.login(user.getIdentifier(), user.getCredential()));
    }

    //   todo 待删除 测试登陆
    @GetMapping("/2/{u}/{p}/{identityType}")
    public ResponseVo test1(@PathVariable String u, @PathVariable String p, @PathVariable Integer identityType) {
        Assert.notNull(u, "username 不能为空");
        Assert.notNull(p, "password 不能为空");
        Assert.notNull(identityType, "认证类型不能为空");
        return ResponseVo.success(userService.login(u, p, identityType));
    }
}
