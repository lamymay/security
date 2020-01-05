package com.arc.security.core.config.properties.test;

public class SocialProperties {

	/**
	 * 第三方登陆的请求地址，同时也是回调地址
	 */
	private String filterProcessesUrl = "/auth";

	private QQProperties qq = new QQProperties();

	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	public QQProperties getQq() {
		return qq;
	}

	public void setQq(QQProperties qq) {
		this.qq = qq;
	}



}
