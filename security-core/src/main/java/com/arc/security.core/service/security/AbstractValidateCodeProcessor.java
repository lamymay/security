package com.arc.security.core.service.security;

import com.arc.security.core.exception.ValidateCodeException;
import com.arc.security.core.model.ValidateCodeType;
import com.arc.security.core.model.constants.SecurityConstants;
import com.arc.security.core.model.system.ValidateCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 抽象实现：模版方法设计模式：
 * 解决的是固定流程的
 * 验证码生成、发送
 *
 * @author 叶超
 * @since 2019/6/6 0:12
 */
@Slf4j
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

//    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGeneratorMap;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 创建验证码
     *
     * @param request
     * @throws Exception
     */
    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 生成校验码
     * 以来搜索实现的
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        ValidateCodeGenerator validateCodeGenerator = getValidateCodeGenerator(request);
        log.debug("validateCodeGenerator 校验器是={}", validateCodeGenerator);
        return (C) validateCodeGenerator.generateVerifyCode(request);
    }

    /**
     * 获取 ValidateCodeGenerator
     *
     * @param request ServletWebRequest
     * @return ValidateCodeGenerator
     */
    protected ValidateCodeGenerator getValidateCodeGenerator(ServletWebRequest request) {
        String beanId = getValidateCodeGeneratorBeanId(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(beanId);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + beanId + "不存在");
        }
        return validateCodeGenerator;
    }

    /**
     * 根据请求的url获取不同验证码处理器的 beanId
     *
     * @param request ServletWebRequest
     * @return 验证码处理器的beanId
     */
    protected String getValidateCodeGeneratorBeanId(ServletWebRequest request) {
        String aa = StringUtils.substringAfter(getClass().getSimpleName(), "CodeProcessor");
        System.out.println(aa);
        System.out.println(aa);
        System.out.println(aa);
        System.out.println(aa);
        String type = StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
        log.debug("url中最后一段是={}", type);
        log.debug("url中最后一段是={}", type);
        log.debug("url中最后一段是={}", type);
        //type+ValidateCodeGenerator
        return type + ValidateCodeGenerator.class.getSimpleName();
    }


//    /**
//     * 根据请求的url获取校验码的类型
//     *
//     * @param request
//     * @return
//     */
//    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
//        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
//        return ValidateCodeType.valueOf(type.toUpperCase());
//    }


    /**
     * 保存校验码
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        //TODO 直接存imageCode会出错，因为BufferedImage无法序列化的
        // sessionStrategy.setAttribute(request, getSessionKey(request), validateCode.getCode());
        //save code to redis
        log.debug("save code to redis=1234");
    }

    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

//
//    /**
//     * 构建验证码放入session时的key
//     *
//     * @param request
//     * @return
//     */
//    private String getSessionKey(ServletWebRequest request) {
//        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
//    }

//    /**
//     * 根据请求的url获取校验码的类型
//     *
//     * @param request
//     * @return
//     */
//    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
//        String type = StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
//        return ValidateCodeType.valueOf(type.toUpperCase());
//    }


    @SuppressWarnings("unchecked")
    @Override
    public boolean verifyCode(ServletWebRequest request) {
        //获取数据-->先获取数据在redis中的key
        //解析数据
        //比对数据
        //ValidateCodeType   ProcessorType

        //根据不同请求地址尝试从请求中获取用户输入的验证码数据，即：请求 /code/sms 则从 smsCode中获取表单输入的数据，请求 /code/image则从 imageCode中获取用户输入数据
        ValidateCodeType processorType = ValidateCodeType.getValidateCodeType(request);

        String onValidateIsKeyInRequest = processorType.getParamNameOnValidate();
        String codeInRequest = null;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), onValidateIsKeyInRequest);
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
            String get_data_from_client_input_error = "获取用户输入的验证码失败";
            log.error(get_data_from_client_input_error + "={}", e);
            throw new ValidateCodeException(get_data_from_client_input_error);
        }

        if (codeInRequest == null) {
            throw new ValidateCodeException("验证码为空");
        } else {
            log.debug("获取到用户输入的验证码 verifyCode={}", codeInRequest);
            String redisValue = null;
            try {
                //尝试获取验证码
                HttpServletRequest httpServletRequest = request.getRequest();
                String redisKey = null;
                if (httpServletRequest.getCookies() != null) {
                    for (Cookie cookie : httpServletRequest.getCookies()) {
                        if (SecurityConstants.SESSION_KEY_PREFIX.equalsIgnoreCase(cookie.getName())) {
                            redisKey = cookie.getValue();
                            break;
                        }
                    }
                }
                ValidateCode validateCode = (ValidateCode) redisTemplate.opsForValue().get(redisKey);
//                redisValue  = (String) redisTemplate.opsForValue().get(redisKey);
                redisValue = validateCode.getCode();
                log.debug("#############################################################");
                log.debug("#############################################################");
                log.debug("validateCode.getCode()结果={}", redisKey);
                log.debug("#############################################################");
                log.debug("#############################################################");
                redisValue = "1234";
                log.debug("The cookieValue is redisKey={},redisValue={}", redisKey, redisValue);

            } catch (Exception e) {
                e.printStackTrace();
                throw new ValidateCodeException("验证码失效");
            }
            //获取redis中的值与前端传入的值比对
            if (codeInRequest.equals(redisValue)) {
                return true;
            } else {
                throw new ValidateCodeException("验证码错误");
            }
        }


    }

    //void send(ServletWebRequest request, ValidateCode validateCode) throws Exception;
    public static void main(String[] args) {
        String type = "sms";
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        System.out.println(generatorName);
    }
}
