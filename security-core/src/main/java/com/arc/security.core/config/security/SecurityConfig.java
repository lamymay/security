package com.arc.security.core.config.security;

import com.arc.security.core.config.properties.ArcSecurityProperties;
import com.arc.security.core.config.security.filter.ImageValidateCodeFilter;
import com.arc.security.core.config.security.filter.SmsValidateCodeFilter;
import com.arc.security.core.config.security.handler.MyAuthenticationFailureHandler;
import com.arc.security.core.config.security.handler.MyAuthenticationSuccessHandler;
import com.arc.security.core.model.constants.SecurityConstants;
import com.arc.security.core.service.security.impl.SecuritySysUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author may
 * @since 2019/5/7 22:31
 */
@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private ArcSecurityProperties arcSecurityProperties;
    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 短信登陆相关
     */
    @Resource
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;


    /**
     * 配置
     *
     * @param http HttpSecurity
     * @throws Exception Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //        添加过滤器
        ImageValidateCodeFilter imageCodeFilter = new ImageValidateCodeFilter();
        imageCodeFilter.setRedisTemplate(redisTemplate);
        imageCodeFilter.setArcSecurityProperties(arcSecurityProperties);
        imageCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        // 调用一下覆盖的方法，完成初始化过滤的url集合的组装
        imageCodeFilter.afterPropertiesSet();

        SmsValidateCodeFilter smsValidateCodeFilter = new SmsValidateCodeFilter();
        smsValidateCodeFilter.setRedisTemplate(redisTemplate);
        smsValidateCodeFilter.setArcSecurityProperties(arcSecurityProperties);
        smsValidateCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        // 调用一下覆盖的方法，完成初始化过滤的url集合的组装
        smsValidateCodeFilter.afterPropertiesSet();
        http.apply(smsCodeAuthenticationSecurityConfig);

        http
                .addFilterBefore(smsValidateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class)

                .formLogin()
                .loginPage(arcSecurityProperties.getBrowser().getLoginUrl())
                .loginProcessingUrl(arcSecurityProperties.getBrowser().getLoginProcessingUrl())
                // 自定义登录成功处理
                // 自定义登录失败处理
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers(
                        //"/",
//                        "/verify/code/image",
//                        "/verify/code/sms",
                        "/verify/**",
                        "/verify/info",
                        "/info/**",
                        "/redis/**",
                        "/map",
                        "/init",
                        "/authentication/cellphone",
                        arcSecurityProperties.getBrowser().getLoginUrl(),
                        arcSecurityProperties.getBrowser().getLoginProcessingUrl(),
                        SecurityConstants.FAVICON_ICO
                ).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .rememberMe().tokenValiditySeconds(172800).tokenRepository(persistentTokenRepository())

        ;

        System.err.println("---------------------------------------------------------------------------");
        System.err.println(arcSecurityProperties.getBrowser().getLoginUrl());
        System.err.println(arcSecurityProperties.getBrowser().getLoginProcessingUrl());
        System.err.println("---------------------------------------------------------------------------");
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * 密码加密解密
     *
     * @return BCryptPasswordEncoder
     */
    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Resource
    private DataSource dataSource;

    @Bean(name = "userDetailsService")
    public UserDetailsService securitySysUserService() {
        return new SecuritySysUserServiceImpl();
    }

}


