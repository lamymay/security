package com.arc.security.core.service.security;

import com.arc.security.core.model.constants.SecurityConstants;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码校验处理器，封装不同校验码的处理逻辑
 * 作用：签发&校验 验证码
 *
 * @author 叶超
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = SecurityConstants.SESSION_KEY_PREFIX;

    /**
     * 创建校验码
     *
     * @param request
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     *
     * @param servletWebRequest ServletWebRequest
     * @return boolean
     */
    boolean verifyCode(ServletWebRequest servletWebRequest);

}
