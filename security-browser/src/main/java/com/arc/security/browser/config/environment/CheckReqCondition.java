//package com.arc.security.browser.config.environment;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Condition;
//import org.springframework.context.annotation.ConditionContext;
//import org.springframework.core.type.AnnotatedTypeMetadata;
//import org.springframework.util.StringUtils;
//
///**
// * Function:校验是否有 redis 配置
// *
// * @author 叶超
// * @since JDK 1.8
// */
//public class CheckReqCondition implements Condition {
//
//    private static Logger log = LoggerFactory.getLogger(CheckReqCondition.class);
//
//
//    @Override
//    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
//        //如果没有加入redis配置的就返回false
//        String property = context.getEnvironment().getProperty("spring.redis.host");
//        String clusterProperty = context.getEnvironment().getProperty("spring.redis.cluster.nodes");
//
//
//        if (StringUtils.isEmpty(property) && StringUtils.isEmpty(clusterProperty)) {
//            log.warn("Need to configure redis!");
//            log.debug("#################################");
//            log.debug("提示 缺少redis或者redis配置有误");
//            log.debug("#################################");
//            return false;
//        } else {
//            log.debug("#################################");
//            log.debug("提示 检测到redis 配置OK，{},{}",property,clusterProperty);
//            log.debug("#################################");
//            return true;
//        }
//    }
//}
