package com.arc.security.core.config.properties.test;

/**
 * 短信验证码属性
 * @author JZH
 *
 */
public class SmsCodeProperties {

	private int length = 6;

	private int expireIn = 60;

	private String urls = "";

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	@Override
	public String toString() {
		return "SmsCodeProperties [length=" + length + ", expireIn=" + expireIn + "]";
	}

	public SmsCodeProperties(int length, int expireIn) {
		super();
		this.length = length;
		this.expireIn = expireIn;
	}

	public SmsCodeProperties() {
		super();
	}



}
