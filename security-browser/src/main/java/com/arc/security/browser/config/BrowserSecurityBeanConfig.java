//package com.arc.security.browser.config;
//
//import com.arc.security.core.config.properties.arc.ArcSecurityProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.web.session.InvalidSessionStrategy;
//import org.springframework.security.web.session.SessionInformationExpiredStrategy;
//
//@Configuration
//public class BrowserSecurityBeanConfig {
//
//    @Autowired
//    private ArcSecurityProperties securityProperties;
//
//    @Bean
//    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
//    public InvalidSessionStrategy invalidSessionStrategy() {
//        return new com.whyalwaysmea.browser.session.MyInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
//    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
//        return new com.whyalwaysmea.browser.session.MyExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
//    }
//
//}
