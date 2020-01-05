package com.arc.security.browser.test;

import com.imooc.security.browser.support.SimpleResponse;
import com.imooc.security.browser.support.SocialUserInfo;
import com.imooc.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController {

	//请求需要认证的时候，再跳转认证界面之前，请求信息会存到RequestCache对象中
	private RequestCache requestCache = new HttpSessionRequestCache();

	private Logger logger = LoggerFactory.getLogger(getClass());

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private ProviderSignInUtils providerSignInUtils;

	/**
	 * 处理需要身份认证的请求，当需要身份认证时，跳转到这
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/authentication/require")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED) //401
	public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if(null != savedRequest) {
			String target = savedRequest.getRedirectUrl();
			logger.info("引发跳转的请求是："+target);
			if(StringUtils.endsWithIgnoreCase(target, ".html")) {
				redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
			}
		}
		return new SimpleResponse("需要身份认证后才能访问，请先登陆！");
	}


	@GetMapping("/socailuser")
	public SocialUserInfo getSocialUserInfo(HttpServletRequest resolver) {
		@SuppressWarnings("rawtypes")
		Connection connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(resolver));
		ConnectionKey key = connection.getKey();
		return new SocialUserInfo(key.getProviderId(), key.getProviderUserId(), connection.getDisplayName(), connection.getImageUrl());
	}

	/**
	 * session过期跳转
	 */
	@GetMapping("/session/invalide")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse sessionValidate() {
		String msg = "Session失效，请重新登陆！";
		return new SimpleResponse(msg);
	}

}
