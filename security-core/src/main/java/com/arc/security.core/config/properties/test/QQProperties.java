package com.arc.security.core.config.properties.test;

/**
 * QQ登陆配置
 * @author JZH
 *
 * 父类有两个属性：
 * private String appId
 * private String appSecret
 *
 */
public class QQProperties extends SocialProperties {

	private String providerId = "qq"; //供应商名（默认为qq，与用户ID、..组成主键）

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

}
