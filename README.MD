#1. Security
##### 设计原则：以增量的方式适应变化--加了新的代码即可修改逻

```$java

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
    
```

说明：顺序
SecurityContextPersistenceFilter<黄色>

UsernamePasswordAuthenticationFilter   <1绿色的>
BasicAuthenticationFilter                           <2绿色的>
RememberMeAuthenticationFilter            <3绿色的>
.
https://www.jianshu.com/p/6df285f30a79
```$java
.rememberMe().tokenValiditySeconds(2419200).tokenRepository(persistentTokenRepository());
```

Error:(105, 16) java: 无法访问org.springframework.jdbc.core.support.JdbcDaoSupport
  找不到org.springframework.jdbc.core.support.JdbcDaoSupport的类文件


图片短信验证码





#2. rbac部分
    
    
user
role
resource


user_group
role_group
resource_group
resource_url
resource_html
resource_file












-----------------------------------------------------------
序列化ID的作用：  
       其实，这个序列化ID起着关键的作用，它决定着是否能够成功反序列化！简单来说，java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。在进行反序列化时，JVM会把传来的字节流中的serialVersionUID与本地实体类中的serialVersionUID进行比较，如果相同则认为是一致的，便可以进行反序列化，否则就会报序列化版本不一致的异常。等会我们可以通过代码验证一下。


       序列化ID如何产生：
       当我们一个实体类中没有显示的定义一个名为“serialVersionUID”、类型为long的变量时，Java序列化机制会根据编译时的class自动生成一个serialVersionUID作为序列化版本比较，这种情况下，只有同一次编译生成的class才会生成相同的serialVersionUID。譬如，当我们编写一个类时，随着时间的推移，我们因为需求改动，需要在本地类中添加其他的字段，这个时候再反序列化时便会出现serialVersionUID不一致，导致反序列化失败。那么如何解决呢？便是在本地类中添加一个“serialVersionUID”变量，值保持不变，便可以进行序列化和反序列化。

java类中serialversionuid 作用 是什么?举个例子说明
serialVersionUID适用于Java的序列化机制。简单来说，Java的序列化机制是通过判断类的serialVersionUID来验证版本一致性的。在进行反序列化时，JVM会把传来的字节流中的serialVersionUID与本地相应实体类的serialVersionUID进行比较，如果相同就认为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常，即是InvalidCastException。

具体的序列化过程是这样的：序列化操作的时候系统会把当前类的serialVersionUID写入到序列化文件中，当反序列化时系统会去检测文件中的serialVersionUID，判断它是否与当前类的serialVersionUID一致，如果一致就说明序列化类的版本与当前类版本是一样的，可以反序列化成功，否则失败。

serialVersionUID有两种显示的生成方式：        
一是默认的1L，比如：private static final long serialVersionUID = 1L;        
二是根据类名、接口名、成员方法及属性等来生成一个64位的哈希字段，比如：        
private static final  long   serialVersionUID = xxxxL;

当一个类实现了Serializable接口，如果没有显示的定义serialVersionUID，Eclipse会提供相应的提醒。面对这种情况，我们只需要在Eclipse中点击类中warning图标一下，Eclipse就会      自动给定两种生成的方式。如果不想定义，在Eclipse的设置中也可以把它关掉的，设置如下：
Window ==> Preferences ==> Java ==> Compiler ==> Error/Warnings ==> Potential programming problems
将Serializable class without serialVersionUID的warning改成ignore即可。

当实现java.io.Serializable接口的类没有显式地定义一个serialVersionUID变量时候，Java序列化机制会根据编译的Class自动生成一个serialVersionUID作序列化版本比较用，这种情况下，如果Class文件(类名，方法明等)没有发生变化(增加空格，换行，增加注释等等)，就算再编译多次，serialVersionUID也不会变化的。

如果我们不希望通过编译来强制划分软件版本，即实现序列化接口的实体能够兼容先前版本，就需要显式地定义一个名为serialVersionUID，类型为long的变量，不修改这个变量值的序列化实体都可以相互进行串行化和反串行化。

下面用代码说明一下serialVersionUID在应用中常见的几种情况。



java面试之String源码中equals具体实现
```$java
public boolean equals(Object anObject) {
        if (this == anObject) {//比较两个对象的地址
            return true;
        }
        if (anObject instanceof String) {//判断该对象是否是String类型
            String anotherString = (String)anObject;
            int n = value.length;
            if (n == anotherString.value.length) {//比较两个字符串的长度是否相等
                char v1[] = value;
                char v2[] = anotherString.value;
                int i = 0;
                while (n-- != 0) {
                    if (v1[i] != v2[i])//比较两个字符串中的每一个字符是否相等
                        return false;
                    i++;
                }
                return true;
            }
        }
        return false;
    }
```



```text
非事物的多步操作怎么保证原子性，锁机制--超卖
超时调用哪个可能造成service层耗尽数据库连接数，调整事物的颗粒度，
关于异常处理--有能力处理--有责任处理
调用逻辑组织数据响应




``` 


```text

从文明史的角度评价改革开放的“维新模式”

改革开放三十年以来，中国从全能主义计划体制转变为政府主导下的市场经济模式。中国现代化进入了真正的起飞阶段，改革三十多年，是自鸦片战争以来中国国运最好的历史时期。中国改革开放的意义就在于，一方面，开明的威权体制，在政治层面上，通过强有力的政府权威来稳定社会，防止出现政治参与的过度膨胀，并运用政府权威来推进改革开放。另一方面，市场经济与社会多元化过程，激活了社会内部的微观个体、地方与企业的竞争活力，社会成员、企业、社团共同体、各省、各县、各乡镇，各村与个体，从板块型的计划体制中离析了出来，它们在市场经济领域与社会生活领域中，成为相对自主、相对多元的利益主体。这就使一种小规模、多元性、自主性与流动性相结合而形成的竞争性机制，在中华大地上得以重新演化出来。这样的一种体制，我们也称之为“中国模式”。
当然，威权体制下的强政府在很大程度上是以人治原则为其施政基础的。而人治的有效性，在很大程度上有赖于政治精英的个人因素，对于一个民族的长治久安来说，这肯定是不稳定的，如何从人治向法制转变，也将是今后中国面临的大课题。随着社会多元化程度的提升，进一步适应这种社会多元性的制度变革也将水到渠成地到来。这是时代的大趋势。

```
