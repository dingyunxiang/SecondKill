package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 配置Spring和junit整合,junit启动时加载Spring IOC容器
 * spring-test,junti
 */

@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit,Spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;



    @Test
    public void testReduceNumber() throws Exception {
        Date date = new Date();
        int count = seckillDao.reduceNumber(1000L,date);
        System.out.println(count);
    }

    @Test
    public void testQueryById() throws Exception {
        long id = 1004;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill);
        /**
         * Seckill{seckillId=1003,
         * name='瞬间抢票',
         * number=200,
         * startTime=Sat Jun 25 13:00:00 CST 2016,
         * endTime=Sun Jun 26 13:00:00 CST 2016,
         * createTime=Sun Jun 26 04:35:11 CST 2016}
         *
         */
    }

    @Test
    public   void testQueryAll() throws Exception {
        List<Seckill> list = seckillDao.queryAll(0,100);
        for(Seckill s:list){
            System.out.println(s);
        }
    }
}