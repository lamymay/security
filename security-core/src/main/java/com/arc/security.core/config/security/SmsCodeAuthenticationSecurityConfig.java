package com.arc.security.core.config.security;

import com.arc.security.core.config.security.filter.SmsAuthenticationFilter;
import com.arc.security.core.config.security.handler.MyAuthenticationFailureHandler;
import com.arc.security.core.config.security.provider.SmsAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 叶超
 * @since 2019/6/9 23:53
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        smsAuthenticationFilter.setAuthenticationManager(authenticationManager);
        smsAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailsService(userDetailsService);

        //把自定义的 smsAuthenticationProvider 加入框架
        http.authenticationProvider(smsAuthenticationProvider)
                .addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
