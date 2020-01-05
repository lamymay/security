package com.arc.security.browser.config;

import com.arc.security.core.config.security.AbstractChannelSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//import org.springframework.security.web.session.InvalidSessionStrategy;
//import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/17 22:10
 * @Description:
 */
@Configuration
public class BrowerSecurityConfig extends AbstractChannelSecurityConfig {

//    @Autowired
//    private SecurityProperties securityProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();          // 关闭csrf防护

    }



//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        applyPasswordAuthenticationConfig(http);
//
//        http.apply(validateCodeSecurityConfig)
//                    .and()
//                .apply(smsCodeAuthenticationSecurityConfig)
//                    .and()
//                .rememberMe()                                   // 记住我相关配置
//                    .tokenRepository(persistentTokenRepository())
//                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
//                    .userDetailsService(myUserDetailsService)
//                    .and()
//                .sessionManagement()
//                    .invalidSessionStrategy(invalidSessionStrategy) // session超时跳转
//                    .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions()) // 最大并发session
//                    .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())    // 是否阻止新的登录
//                    .expiredSessionStrategy(sessionInformationExpiredStrategy)      // 并发session失效原因
//                    .and()
//                    .and()
//                .csrf().disable();          // 关闭csrf防护
//
//        authorizeConfigManager.config(http.authorizeRequests());
//
//    }
//
    //
//    @Autowired
//    private DataSource dataSource;


    //
//    @Autowired
//    private UserDetailsService myUserDetailsService;
//
//    @Autowired
//    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
//
//    @Autowired
//    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
//
//    @Autowired
//    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
//
//    @Autowired
//    private InvalidSessionStrategy invalidSessionStrategy;
//
//    @Autowired
//    private AuthorizeConfigManager authorizeConfigManager;


}
