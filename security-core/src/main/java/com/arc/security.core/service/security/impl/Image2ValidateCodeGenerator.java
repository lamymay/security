package com.arc.security.core.service.security.impl;

import com.arc.security.core.config.properties.arc.ArcSecurityProperties;
import com.arc.security.core.model.system.ValidateCode;
import com.arc.security.core.service.security.ValidateCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author 叶超
 * @since 2019/6/2 19:20
 */
@Slf4j
//@Service(value = "imageValidateCodeGenerator")
//统一注入@Service
public class Image2ValidateCodeGenerator implements ValidateCodeGenerator {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    //从配置文件中获取配置
    private ArcSecurityProperties arcSecurityProperties;


/*
    @Override
    public Integer getRandomVerifyCodeAsNumber(int contentSize) {
        //如果初始值设定一场给默认值1
        contentSize = contentSize < 1 ? 1 : contentSize;
        Random random = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(contentSize);
        for (int i = 0; i < contentSize; i++) {
            verifyCode.append(random.nextInt(contentSize));
        }
        return Integer.valueOf(String.valueOf(verifyCode));
    }

    @Override

    }


    @Override
    public ValidateCode generateVerifyCode(int contentSize) {
        String code = getRandomVerifyCode(contentSize);
        ValidateCode validateCode = new ValidateCode(code, 600);
        log.debug("代码中生成的ValidateCode={}", validateCode);
        return validateCode;
    }*/


    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    @Override
    public ValidateCode generateVerifyCode(ServletWebRequest request) {
        int expiredSecond = arcSecurityProperties.getCode().getSms().getExpiredSecond();
        String code = getRandomVerifyCode(arcSecurityProperties.getCode().getSms().getLength());
        ValidateCode validateCode = new ValidateCode();
        validateCode.setCode(code);
        validateCode.setExpiredSecond(expiredSecond);
        return validateCode;
    }

    public String getRandomVerifyCode(int contentSize) {
        //如果初始值设定一场给默认值1
        contentSize = contentSize < 1 ? 1 : contentSize;
        Random random = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(contentSize);
        for (int i = 0; i < contentSize; i++) {
            verifyCode.append(random.nextInt(contentSize));
        }
        return String.valueOf(verifyCode);
    }

    private Image2ValidateCodeGenerator() {
    }

    public Image2ValidateCodeGenerator(ArcSecurityProperties securityProperties) {
        this.arcSecurityProperties = securityProperties;
    }
}
