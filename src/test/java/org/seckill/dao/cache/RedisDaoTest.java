package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by dingyunxiang on 16/6/27.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class RedisDaoTest {

    long id = 1004L;
    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private RedisDao redisDao;


    @Test
    public void testGetSeckill() throws Exception {

        Seckill seckill = redisDao.getSeckill(id);
        if(seckill == null){
            seckill = seckillDao.queryById(id);
            if(seckill!=null){
                String rs = redisDao.putSeckill(seckill);
                System.out.println(rs);
                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
        System.out.println(seckill);

    }

    @Test
    public void testPutSeckill() throws Exception {

    }
}