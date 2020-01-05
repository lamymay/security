package com.arc.security.core.config.properties.test;

public class BrowserProperties {

	private SessionProperties session = new SessionProperties();

	private String loginPage = "/imooc-signIn.html";

	private LoginType loginType = LoginType.JSON;

	private int rememberMeSeconds = 60*60*24*7;

	/**
	 * 注册页
	 */
	private String signUpUrl = "/imooc-signUp.html";

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public int getRememberMeSeconds() {
		return rememberMeSeconds;
	}

	public void setRememberMeSeconds(int rememberMeSeconds) {
		this.rememberMeSeconds = rememberMeSeconds;
	}

	public String getSignUpUrl() {
		return signUpUrl;
	}

	public void setSignUpUrl(String signUpUrl) {
		this.signUpUrl = signUpUrl;
	}

	public SessionProperties getSession() {
		return session;
	}

	public void setSession(SessionProperties session) {
		this.session = session;
	}
}
