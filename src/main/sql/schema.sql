


CREATE DATABASE seckill;

-- 使用数据库;
use seckill;

-- 创建秒杀库存表

CREATE TABLE seckill(
  seckill_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
  name VARCHAR(120) NOT NULL COMMENT '商品名称',
  number INT NOT NULL COMMENT '库存数量',
  start_time TIMESTAMP NULL COMMENT '秒杀开始时间',
  end_time TIMESTAMP NULL COMMENT '秒杀结束时间',
  create_time TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET = utf8 COMMENT='秒杀库存表';
-- 不报错版本
CREATE TABLE `seckill1` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '秒杀开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- 初始化数据
insert into
  seckill(name,number,start_time,end_time)
values
  ('1000元秒杀iphone6',100,'2016-06-20 00:00:00','2016-06-20 00:00:00');


-- 显示如何创建
# show CREATE table seckill\G




-- 秒杀成功明细表
-- 用户登录认证相关信息
create table success_killed(
  seckill_id bigint NOT NULL,
  user_phone bigint NOT NULL,
  state TINYINT NOT NULL DEFAULT  -1 COMMENT '状态表示:-1:无效  0:成功  1:已付款  2:已发货',
  create_time TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone), /*联合主键*/
  key idx_creare_time(create_time)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT ='秒杀成功明细表';


-- 连接数据库控制台
# mysql -u root -p


-- 为什么手写DDL
-- 记录每次上线的DDL修改
-- 上线v1.1
ALTER TABLE   seckill
DROP index idx_create_time,
ADD index idx_c_s(start_time,create_time);


-- 上线1.2
-- DDL
