//package com.arc.security.browser.config.interceptor;
//
//import com.crossoverJie.request.check.model.enums.StatusEnum;
//import com.crossoverJie.request.check.exception.SBCException;
//import com.crossoverJie.request.check.model.properties.CheckReqProperties;
//import com.crossoverJie.request.check.model.req.BaseRequest;
//import com.crossoverJie.request.check.util.StringUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.concurrent.TimeUnit;
//
///**
// * Function:切面
// *
// * @author crossoverJie
// *         Date: 2017/7/31 20:07
// * @since JDK 1.8
// */
////切面注解
//@Aspect
////扫描
//@Component
////开启cglib代理
//@EnableAspectJAutoProxy(proxyTargetClass = true)
//public class ReqNoDrcAspect {
//    private static Logger logger = LoggerFactory.getLogger(ReqNoDrcAspect.class);
//
//    @Autowired
//    private CheckReqProperties properties ;
//
//    private String prefixReq ;
//
//    private long day ;
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    //对依赖的注入参数进行判断并给出默认值，注意这里本来时可以用@Value 代替的
//
//    //、从Java EE5规范开始，Servlet中增加了两个影响Servlet生命周期的注解，@PostConstruct和@PreDestroy，这两个注解被用来修饰一个非静态的void（）方法。写法有如下两种方式：
//    //被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行，init（）方法之前执行。PreDestroy（）方法在destroy（）方法知性之后执行
//
////    spring中Constructor、@Autowired、@PostConstruct的顺序
////    其实从依赖注入的字面意思就可以知道，要将对象p注入到对象a，那么首先就必须得生成对象p与对象a，才能执行注入。所以，如果一个类A中有个成员变量p被@Autowired注解，那么@Autowired注入是发生在A的构造方法执行完之后的。
////
////    如果想在生成对象时候完成某些初始化操作，而偏偏这些初始化操作又依赖于依赖注入，那么就无法在构造函数中实现。为此，可以使用@PostConstruct注解一个方法来完成初始化，@PostConstruct注解的方法将会在依赖注入完成后被自动调用。
////
////    Constructor >> @Autowired >> @PostConstruct
//    @PostConstruct
//    public void init() throws Exception {
//        prefixReq = properties.getRedisKey() == null ? "reqNo" : properties.getRedisKey() ;
//        day = properties.getRedisTimeout() == null ? 1L : properties.getRedisTimeout() ;
//        logger.info("sbc-request-check init......");
//        logger.info(String.format("redis prefix is [%s],timeout is [%s]", prefixReq, day));
//    }
//
//    /**
//     * 切面该注解
//     */
//    @Pointcut("@annotation(com.crossoverJie.request.check.model.anotation.CheckReqNo)")
//    public void checkRepeat(){
//    }
//
//    @Before("checkRepeat()")
//    public void before(JoinPoint joinPoint) throws Exception {
//        BaseRequest request = getBaseRequest(joinPoint);
//        if(request != null){
//            final String reqNo = request.getReqNo();
//            //1 AOP获取请求参数
//            //2 判断 空--返回异常
//            if(StringUtil.isEmpty(reqNo)){
//                throw new SBCException(StatusEnum.REPEAT_REQUEST);
//            }else{
//
//                try {
//                    String tempReqNo = redisTemplate.opsForValue().get(prefixReq +reqNo);
//                    logger.debug("tempReqNo={}" , tempReqNo);
//
//                    if((StringUtil.isEmpty(tempReqNo))){
//                        redisTemplate.opsForValue().set(prefixReq + reqNo, reqNo, day, TimeUnit.DAYS);
//                    }else{
//                        throw new SBCException("请求号重复,"+ prefixReq +"=" + reqNo);
//                    }
//
//                } catch (RedisConnectionFailureException e){
//                    logger.error("redis操作异常",e);
//                    throw new SBCException("need redisService") ;
//                }
//            }
//        }
//
//    }
//
//
//
//    public static BaseRequest getBaseRequest(JoinPoint joinPoint) throws Exception {
//        BaseRequest returnRequest = null;
//        Object[] arguments = joinPoint.getArgs();
//        if(arguments != null && arguments.length > 0){
//            returnRequest = (BaseRequest) arguments[0];
//        }
//        return returnRequest;
//    }
//}
