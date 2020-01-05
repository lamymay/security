package com.arc.security.browser.test.support;

/**
 * 第三方用户信息
 * @author JZH
 *
 */
public class SocialUserInfo {

	private String providerId; //哪一个第三方用户做社交登陆

	private String providerUserId; //用户的openId

	private String nickName; //用户昵称

	private String headImg; //用户头像

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public SocialUserInfo(String providerId, String providerUserId, String nickName, String headImg) {
		super();
		this.providerId = providerId;
		this.providerUserId = providerUserId;
		this.nickName = nickName;
		this.headImg = headImg;
	}

	public SocialUserInfo() {
		super();
	}

	@Override
	public String toString() {
		return "SocialUserInfo [providerId=" + providerId + ", providerUserId=" + providerUserId + ", nickName="
				+ nickName + ", headImg=" + headImg + "]";
	}

}
