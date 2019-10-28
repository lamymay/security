package com.arc.security.core.model.constants;

/**
 * @author 叶超
 * @since 2019/5/22 22:33
 */
public interface SecurityConstants {

    /**
     * 默认登录页面
     */
    String DEFAULT_LOGIN_PAGE_URL = "/login.html";

    /**
     * 当请求需要身份认证时，默认跳转的url
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    /**
     * 默认的用户名密码登录请求处理url
     */
    String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";
    /**
     * 默认的手机验证码登录请求处理url
     */
    String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/authentication/mobile";

    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";

    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /**
     * session失效默认的跳转地址
     */
    String DEFAULT_SESSION_INVALID_URL = "/session/invalid";


    public static final String VERIFY_CODE = "/verify/code";

    public static final String VERIFY_IMAGE_PREFIX = "VERIFY_IMAGE";

    public static final String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE";
    public static final String FAVICON_ICO = "/favicon.ico";

}
