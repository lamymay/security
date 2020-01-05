package com.arc.security.core.config.properties.test;

/**
 * 图形验证码属性
 * @author JZH
 *
 */
public class ImageCodeProperties {

	private int width = 67; //图片宽

	private int height = 23; //图片高

	private int length = 4; //验证码长度

	private int expiredIn = 60; //失效时间

	private String urls = ""; //需要检验的url



	public ImageCodeProperties() {
		super();
	}

	public ImageCodeProperties(int width, int height, int length, int expiredIn) {
		super();
		this.width = width;
		this.height = height;
		this.length = length;
		this.expiredIn = expiredIn;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getExpiredIn() {
		return expiredIn;
	}

	public void setExpiredIn(int expiredIn) {
		this.expiredIn = expiredIn;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}



}
