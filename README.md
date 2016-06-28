# SecondKill
基于J2EE实现的秒杀系统
##具体实现
Spring MVC + Spring + Mybatis 

##高并发点:
暴露URL部分使用redis实现
采用存储过程防止事务导致的行级锁,避免了网络IO和GC

##博客链接
[博客](http://dingyunxiang.cn/2016/06/24/Java%E9%A1%B9%E7%9B%AE%E4%B9%8B%E7%A7%92%E6%9D%80%E7%B3%BB%E7%BB%9F/)
