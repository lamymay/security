RBAC介绍。
RBAC认为授权实际上是Who 、What 、How 三元组之间的关系，也就是Who 对What 进行How 的操作，也就是“主体”对“客体”的操作。

Who：是权限的拥有者或主体（如：User，Role）。
What：是操作或对象（operation，object）。
How：具体的权限（Privilege,正向授权与负向授权）。

然后 RBAC 又分为RBAC0、RBAC1、RBAC2、RBAC3

RBAC0：是RBAC的核心思想。
RBAC1：是把RBAC的角色分层模型。
RBAC2：增加了RBAC的约束模型。
RBAC3：其实是RBAC2 + RBAC1。

[来源：简书](https://www.jianshu.com/p/38d0d2adb265)


1登录
就是去根据唯一用户用户标识查找到一条数据，并比较密码是否正确
可能的多挣认证规则：
username/password
email/password
sso
WeChat
QQ
支付宝
淘宝
京东
百度
网易
微博

2鉴权
系统可能是无限权限访问的可直接放过去
否则需要参考RBAC设计相关服务
