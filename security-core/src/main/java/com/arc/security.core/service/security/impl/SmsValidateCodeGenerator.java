package com.arc.security.core.service.security.impl;

import com.arc.security.core.config.properties.ArcSecurityProperties;
import com.arc.security.core.model.ValidateCodeType;
import com.arc.security.core.model.system.ValidateCode;
import com.arc.security.core.service.security.ValidateCodeGenerator;
import com.arc.security.core.service.sys.SmsSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 长度和过期时间可配置
 *
 * @author 叶超
 * @since 2019/6/2 19:20
 */
@Slf4j
//@Service(value = "smsValidateCodeGenerator")
//统一注入@Service
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {

    /**
     * 短信发送
     */
    @Resource
    private SmsSenderService smsSenderService;


    /**
     * 系统中所有的{@link ValidateCodeGenerator} 接口的实现
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGeneratorMap;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    //从配置文件中获取配置
    private ArcSecurityProperties arcSecurityProperties;


//    @Override
//    public void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
//        if (validateCode != null && request != null) {
//            long cellphone = ServletRequestUtils.getRequiredLongParameter(request.getRequest(), "cellphone");
//            smsSenderService.sendSms(cellphone, validateCode.getCode());
//        }
//    }


    /**
     * 生成验证码
     * 技巧：Spring依赖查找
     *
     * @param request
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public ValidateCode generateVerifyCode(ServletWebRequest request) {

        log.debug("#############################################################");
        log.debug("结果smsSenderService={}", smsSenderService);
        log.debug("结果redisTemplate={}", redisTemplate);
        log.debug("结果validateCodeGeneratorMap={}", validateCodeGeneratorMap);
        log.debug("结果arcSecurityProperties={}", arcSecurityProperties);
        log.debug("#############################################################");

        String code = randomNumeric(arcSecurityProperties.getCode().getSms().getLength());

        String type = ValidateCodeType.getValidateCodeType(request).toString();
        log.debug("getProcessorType(request) 结果={}", type);
        //"imageValidateCodeGenerator"
        String keyValidateCodeGenerator = "type" + "ValidateCodeGenerator";
        if (validateCodeGeneratorMap != null) {
            ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(keyValidateCodeGenerator);
            log.debug("结果={}", validateCodeGenerator);
        }

        //生成验证码
        ValidateCode smsValidateCode = new ValidateCode();
        smsValidateCode.setCode(code);
        smsValidateCode.setExpiredSecond(arcSecurityProperties.getCode().getSms().getExpiredSecond());

        String key = UUID.randomUUID().toString().replaceAll("-", "");
        String valueInRedis = smsValidateCode.getCode();
        try {

            redisTemplate.opsForValue().set(key, smsValidateCode, smsValidateCode.getExpiredSecond(), TimeUnit.SECONDS);
        } catch (Exception e) {
            System.err.println("----------------------------------------");
            e.printStackTrace();
            log.error("结果={}", e);
            System.err.println("----------------------------------------");
        }
        log.info("imageValidateCode={}\nredis的key={},value={}位验证码:{}", smsValidateCode, key, valueInRedis.length(), valueInRedis);
        System.err.println("#############################################################");
        // 单位是秒  43200S=12h
        Cookie cookie = new Cookie("COOKIE_KEY_FOR_SMS", key);
        cookie.setPath("/");
        cookie.setMaxAge(43200);
        HttpServletResponse response = request.getResponse();
        response.addCookie(cookie);
        response.addCookie(cookie);
        response.addCookie(cookie);


        long to = ServletRequestUtils.getLongParameter(request.getRequest(), "cellphone", 18674192466L);

        log.debug("cellphone结果={}", to);
        log.debug("cellphone结果={}", to);
        log.debug("cellphone结果={}", to);

        smsSenderService.sendSms(to, smsValidateCode.getCode());


        return smsValidateCode;
    }


    //  int expiredSecond = arcSecurityProperties.getCode().getSms().getExpiredSecond();
    //        String code = getRandomVerifyCode(arcSecurityProperties.getCode().getSms().getLength());
    //
    //        ValidateCode validateCode = new ValidateCode( );
    //        validateCode.setCode(code);
    //        validateCode.setExpiredSecond(expiredSecond);
    //

    //    //    @Override
//    public String getRandomVerifyCode(int contentSize) {
//        //如果初始值设定一场给默认值1
//        contentSize = contentSize < 1 ? 1 : contentSize;
//        Random random = new Random(System.currentTimeMillis());
//        StringBuilder verifyCode = new StringBuilder(contentSize);
//        for (int i = 0; i < contentSize; i++) {
//            verifyCode.append(random.nextInt(contentSize));
//        }
//        return String.valueOf(verifyCode);
//    }


////    @Override
//    public ValidateCode generateVerifyCode(int contentSize) {
//        String code = getRandomVerifyCode(contentSize);
//        ValidateCode validateCode = new ValidateCode(code, 600);
//        log.debug("代码中生成的ValidateCode={}", validateCode);
//        return validateCode;
//    }

    private static final Random RANDOM = new Random();

    ////
    public static String randomNumeric(int count) {
        return random(count, false, true);
    }

    public static String random(int count, boolean letters, boolean numbers) {
        return random(count, 0, 0, letters, numbers);
    }

    public static String random(int count, int start, int end, boolean letters, boolean numbers) {
        return random(count, start, end, letters, numbers, (char[]) null, RANDOM);
    }

    public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars, Random random) {
        if (count == 0) {
            return "";
        } else if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        } else {
            if (start == 0 && end == 0) {
                end = 123;
                start = 32;
                if (!letters && !numbers) {
                    start = 0;
                    end = 2147483647;
                }
            }

            char[] buffer = new char[count];
            int gap = end - start;

            while (true) {
                while (true) {
                    while (count-- != 0) {
                        char ch;
                        if (chars == null) {
                            ch = (char) (random.nextInt(gap) + start);
                        } else {
                            ch = chars[random.nextInt(gap) + start];
                        }

                        if (letters && Character.isLetter(ch) || numbers && Character.isDigit(ch) || !letters && !numbers) {
                            if (ch >= '\udc00' && ch <= '\udfff') {
                                if (count == 0) {
                                    ++count;
                                } else {
                                    buffer[count] = ch;
                                    --count;
                                    buffer[count] = (char) ('\ud800' + random.nextInt(128));
                                }
                            } else if (ch >= '\ud800' && ch <= '\udb7f') {
                                if (count == 0) {
                                    ++count;
                                } else {
                                    buffer[count] = (char) ('\udc00' + random.nextInt(128));
                                    --count;
                                    buffer[count] = ch;
                                }
                            } else if (ch >= '\udb80' && ch <= '\udbff') {
                                ++count;
                            } else {
                                buffer[count] = ch;
                            }
                        } else {
                            ++count;
                        }
                    }

                    return new String(buffer);
                }
            }
        }
    }


    public SmsValidateCodeGenerator(ArcSecurityProperties securityProperties) {
        this.arcSecurityProperties = securityProperties;
    }

}
