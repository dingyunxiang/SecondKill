package org.seckill.dao.cache;

import redis.clients.jedis.JedisPool;

/**
 * Created by dingyunxiang on 16/6/27.
 */
public class RedisDao {

    private JedisPool jedisPool;


    private RedisDao(String ip,int port){

    }

}
