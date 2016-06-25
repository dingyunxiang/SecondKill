


CREATE DATABASE seckill;

-- 使用数据库;
use seckill;

-- 创建秒杀库存表
CREATE TABLE seckill(
  'seckill_id' bit NOT NULL AUTO_InCREMENT ,
  'name' varchar(120) NOT NULL,
  'number' int NOT NULL,
  'start_time' TIMESTAMP  NOT NULL,
  'end_time' TIMESTAMP  NOT NULL,
  'create_time' TIMESTAMP  NOT NULL DEFAULT  CURRENT_TIMESTAMP ,
  PRIMARY  KEY(seckill_id),
  key idx_start_time(start_time),
  key idx_create_time(create_time),
  key idx_end_time(end_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- 初始化数据
insert into
  seckill(name,number,start_time,end_time)
values
  ('1000元秒杀iphone6',100,'2016-06-20 00:00:00','2016-06-20 00:00:00');


-- 秒杀成功明细表
create table success_killed(
  'seckill_id' bit NOT NULL


);