package com.arc.security.core.model;

import com.arc.security.core.model.constants.SecurityConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验时从请求中获取的参数的名字
 *
 * @author may
 */
public enum ValidateCodeType {

    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     *
     * @return
     */
    public abstract String getParamNameOnValidate();

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    public static ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

}
