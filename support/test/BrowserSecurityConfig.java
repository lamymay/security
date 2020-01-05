package com.arc.security.browser.test;

import com.imooc.security.browser.session.ImoocExpiredSessionStrategy;
import com.imooc.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeAuthenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 浏览器认证授权基本配置
 * @author JZH
 *
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private ImoocAuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

	@Autowired
	private ImoocAuthenticationFailureHandler imoocAuthenticationFailureHandler;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 【配置】登陆等其他请求使用验证码校验
	 */
	@Autowired
	private ValidateCodeAuthenticationConfig validateCodeAuthenticationConfig;

	/**
	 * 【配置】手机号登陆
	 * 注入 自定义的一套认证组件配置，注入后，自定义组件生效
	 */
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

	@Autowired
	private SpringSocialConfigurer imoocSocialSecurityConfig;

	/**
	 * 会使用具体应用的数据源
	 */
	@Autowired
	private DataSource dataSource;

	/**
	 * 对用户的token操作（从数据库中存储、取出），实现记住我的功能
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		//tokenRepository.setCreateTableOnStartup(true);//首次启动的时候创建表
		return tokenRepository;
	}

	/**
	 * 密码加密解密
	 *
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub

		//配置
		http
		//1. 引入其他配置
		.apply(smsCodeAuthenticationSecurityConfig) //手机号登陆
		.and()
		.apply(validateCodeAuthenticationConfig) // 验证码校验（注释掉即不会验证）
		.and()
		.apply(imoocSocialSecurityConfig)
		.and()
		//2. 登录名、密码登陆方式配置
		.formLogin()
			.loginPage("/authentication/require") // 需要身份认证的时候跳转的URL（可以直接指定html）
			.loginProcessingUrl("/authentication/form") // 指定登陆请求url
			.successHandler(imoocAuthenticationSuccessHandler) // 指定登陆成功的处理方式是自定义实现
			.failureHandler(imoocAuthenticationFailureHandler) //指定登陆失败的处理方式是自定义实现
		.and()
		//3. 记住我功能配置
		.rememberMe()
			.tokenRepository(persistentTokenRepository())
			.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
			.userDetailsService(userDetailsService) //获取token并从数据库中取出用户名后需要进行登陆
			// http.httpBasic()
		.and()
		//4. session配置
		.sessionManagement()
			.invalidSessionUrl("/session/invalide") //session过期后跳转的url
			.maximumSessions(securityProperties.getBrowser().getSession().getMaxNum()) //同一个用户最多可以同时登陆几个客户端
			.expiredSessionStrategy(new ImoocExpiredSessionStrategy())
			.maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().getMaxPrevent()) // session登陆的数量达到最大数量后，阻止后续登陆行为；默认false
		.and()
		.and()
		//5. 授权的配置
		.authorizeRequests()
			.antMatchers("/authentication/require", securityProperties.getBrowser().getLoginPage(), "/code/*", securityProperties.getBrowser().getSignUpUrl(), "/user/regist", "/session/invalide").permitAll() // 跳转URL、访问登陆界面不需要授权认证
			.anyRequest() // 任何请求
			.authenticated().and().csrf().disable() // 需要身份认证
		;
	}

}
