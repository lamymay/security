package com.arc.security.core.config.security.code;

import com.arc.security.core.config.properties.ArcSecurityProperties;
import com.arc.security.core.service.security.ValidateCodeGenerator;
import com.arc.security.core.service.security.impl.ImageValidateCodeGenerator;
import com.arc.security.core.service.security.impl.SmsValidateCodeGenerator;
import com.arc.security.core.service.sys.SmsSenderService;
import com.arc.security.core.service.sys.impl.DefaultSmsSenderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 叶超
 * @since 2019/6/3 22:08
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private ArcSecurityProperties securityProperties;

    /**
     * 注意： 不存在一个名字叫做imageValidateCodeGenerator的bean的时候在从这里组装bean
     * 方便后来者去覆盖我们的验证码生成逻辑
     * 以增量的方式适应变化--加了新的代码即可修改逻辑
     *
     * @return ValidateCodeGenerator
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        return new ImageValidateCodeGenerator(securityProperties);
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsValidateCodeGenerator")
    public ValidateCodeGenerator smsValidateCodeGenerator() {
        SmsValidateCodeGenerator smsValidateCodeGenerator = new SmsValidateCodeGenerator(securityProperties);

        return smsValidateCodeGenerator;
    }

    //发送短信的服务
    @Bean
    @ConditionalOnMissingBean(SmsSenderService.class)
    public SmsSenderService smsSenderServiceImpl() {
        return new DefaultSmsSenderServiceImpl();
    }

}
