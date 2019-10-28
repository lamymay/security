package com.arc.security.core.config.properties.code;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 叶超
 * @since 2019/5/22 21:47
 */
@Setter
@Getter
@ToString
//@Component//@ConfigurationProperties(prefix = "arc.security.code.image")
public class ImageCodeProperties extends SmsCodeProperties {

    private int width = 60;

    private int height = 40;

    //private int expiredSecond = 60;

    //private String url;

    public ImageCodeProperties() {
        setLength(4);
    }
}
