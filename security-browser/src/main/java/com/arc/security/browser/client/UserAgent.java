package com.arc.security.browser.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Role Based Access Control 基于角色的访问控制
 * https://www.jianshu.com/p/38d0d2adb265
 *
 * @author 叶超
 * @since 2019/10/29 9:23
 */
public class UserAgent {

    //远程调用 接口实现登录
    public Map login(String username, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", "1");
        map.put("username", username);
        map.put("password", password);

        return map;
    }


    //查询用户的角色列表
    public Map listRoleListByUserId(Long userId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        List<String> roleGroup = new ArrayList<>();
        //不需要的
        map.put("roleGroup", roleGroup);
        return map;
    }




    //获取该用户的权限
    // user_role   用户 或者 用户组 链接角色或角色组的表
    // 角色或者角色组链接 资源的表
    //select * from role where user_id =XXX
    //select * from resouce where role_id in (XXXX)

    public Map listPermissionListByUserId(Long userId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        List<String> resource_list = new ArrayList<>();
        List<String> resource_api = new ArrayList<>();
        List<String> resource_file = new ArrayList<>();
        List<String> resource_html = new ArrayList<>();
        map.put("resourceList", resource_list);
        map.put("resource_api;", resource_api);
        map.put("resource_file", resource_file);
        map.put("resource_html", resource_html);
        return map;
    }

}
