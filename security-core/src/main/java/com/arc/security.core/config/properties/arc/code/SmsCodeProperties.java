package com.arc.security.core.config.properties.arc.code;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 自动读取配置文件中配置
 * 短信相关属性
 *
 * @author 叶超
 * @since: 2019/5/8 22:24
 */
@Setter
@Getter
@ToString
//@ConfigurationProperties(prefix = "arc.security.browser")
public class SmsCodeProperties implements Serializable {

    public int length =6;

    private int expiredSecond = 240;

    public String url = "/authentication/cellphone";



}
