package com.arc.security.core.config.security.filter;

import com.arc.security.core.model.authentication.SmsAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 做登录
 *
 * @author 叶超
 * @since 2019/6/9 23:22
 */
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    // static fields initializers
    //===========================================================================================

    public static final String ARC_SECURITY_FORM_CELLPHONE_KEY = "cellphone";

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";

    /**
     * 表单提交时候的手机号的key
     */
    private String cellphoneParameter = ARC_SECURITY_FORM_CELLPHONE_KEY;

    /**
     * 表单post提交到这个地址处理登录请求
     */
    public static String ARC_SECURITY_FORM_CELLPHONE_LOGIN_URL = "/authentication/cellphone";

    /**
     * 只允许post
     */
    private boolean postOnly = true;


    // ~Constructor
    //===========================================================================================
    public SmsAuthenticationFilter() {
        super(new AntPathRequestMatcher(ARC_SECURITY_FORM_CELLPHONE_LOGIN_URL, "POST"));
    }

    // ~Methods
    //===========================================================================================

    /**
     * 认证流程
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            //从请求中获取手机号码
            String cellphone = this.obtainCellphone(request);
            if (cellphone == null) {
                cellphone = "";
            }

            cellphone = cellphone.trim();
            SmsAuthenticationToken authRequest = new SmsAuthenticationToken(cellphone);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }


    /**
     * 从请求中获取手机号码
     *
     * @param request
     * @return
     */
    protected String obtainCellphone(HttpServletRequest request) {
        return request.getParameter(this.cellphoneParameter);
    }

    protected void setDetails(HttpServletRequest request, SmsAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setCellphoneParameter(String cellphoneParameter) {
        Assert.hasText(cellphoneParameter, "cellphone  parameter must not be empty or null");
        this.cellphoneParameter = cellphoneParameter;
    }

    public final String getCellphoneParameter() {
        return this.cellphoneParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

}
