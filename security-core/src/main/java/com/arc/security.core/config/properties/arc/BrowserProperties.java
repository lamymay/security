package com.arc.security.core.config.properties.arc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 自动读取配置文件中配置
 * 浏览器相关属性
 *
 * @author 叶超
 * @since: 2019/5/8 22:24
 */
@Setter
@Getter
@ToString
//@ConfigurationProperties(prefix = "arc.security.browser")
public class BrowserProperties  implements Serializable {

    public String loginUrl = "/test_login";

    public String loginProcessingUrl = "/v1/login/form/process";

    public String cookieKey = "COOKIE_KEY_";

    /**
     * 记住我的时间，单位s
     */
    private int rememberMeSeconds = 600;


    //    private String signUpUrl;
    //    private LoginType loginType;

}
