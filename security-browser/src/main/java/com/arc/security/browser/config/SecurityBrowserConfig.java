package com.arc.security.browser.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author may
 * @since 2020/1/5 22:27
 */
public class SecurityBrowserConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests().anyRequest().authenticated();

    }
}


//    @Autowired
//    private SecurityProperties securityProperties;


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
