package com.arc.security.core.config.properties.arc;

import com.arc.security.core.config.properties.arc.code.ValidateCodeProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自动读取配置文件中配置
 *
 * @author 叶超
 * @since: 2019/5/8 22:19
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "arc.security")
public class ArcSecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();
}
