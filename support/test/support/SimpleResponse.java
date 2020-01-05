package com.arc.security.browser.test.support;

import java.io.Serializable;

public class SimpleResponse implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Object content;

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public SimpleResponse(Object content) {
		super();
		this.content = content;
	}

}
