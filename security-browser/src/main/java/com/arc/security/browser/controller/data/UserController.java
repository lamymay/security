package com.arc.security.browser.controller.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关的的接口by RESTful
 *
 * @author yehcao
 * @since 2018/12/25
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    //    @Resource
//    private SysUserService userService;

    @GetMapping(value = "/{id}")
    public Object get(@PathVariable Long id) {
        log.debug("获取单个用户,参数 id={}", id);
        return "id->" + id;
    }

    /**
     * 获取用户列表
     *
     * @return
     */
    @PostMapping(value = "/list")
    public Object list() {
        log.debug("获取用户列表。");
        return "list 获取用户列表 [,,,,,,,,]";
    }


}

