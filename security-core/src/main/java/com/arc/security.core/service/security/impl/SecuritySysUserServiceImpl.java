package com.arc.security.core.service.security.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

/**
 * @author 叶超
 * @since 2019/5/7 22:52
 */
//在配置文件中统一配置 @Service
public class SecuritySysUserServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(SecuritySysUserServiceImpl.class);

//    @Autowired
//    private UserService userService;

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;

    /**
     * 获取数据库中定义的用户
     * 组装Spring Security的 User
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("登陆的用户名是username={}", username);
        //查询数据库获取用户，判断密码是否正确
        //todo 假装用户是从数据库获取到的，So这里的账号是写死的，注意：密码明文在保存的时候加密入库的。这里的pwd是模拟的
        String pwd = passwordEncoder.encode("admin");
        log.debug("加密后的pwd={}", pwd);

        //创建权限集合
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        //        User user = new User(username, pwd, authorities);
        //前两个参数是做认证--> 第三个参吧字符串转换为 权限数组
        User user = new User(username, pwd, true, true, true, true, authorities);


        log.debug("登陆的用户是 \nusername={}\npassword={}\n权限信息={}", user.getUsername(), user.getPassword(), user.getAuthorities().size());
        return user;
    }
}

//java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
//框架提供的BCryptPass
// wordEncoder 有自动变化的盐，保密效果更好，建议使用
// 登陆的 pwd=$2a$10$m9viwi6WjpiO4Rvb42uHNeiUs4cE5JijdpGCICXpbZ75CUOQDW5oy
//登陆的 pwd=$2a$10$aJVMTAcn.9aALHfFPnmjNOdmmf6v1JbjR8yIpDdS7flcygEFtxbSy
