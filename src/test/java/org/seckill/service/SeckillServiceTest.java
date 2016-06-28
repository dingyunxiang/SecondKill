package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by dingyunxiang on 16/6/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}", list);
    }

    @Test
    public void testGetById() throws Exception {
        Seckill seckill = seckillService.getById(1004L);
        logger.info("seckill={}", seckill);
    }


    //    测试代码完整逻辑,可以重复执行
    @Test
    public void testSeckillLogic() throws Exception {
        long id = 1004L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()) {
            logger.info("exposer={}", exposer);
            long phone = 13122104541L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
                logger.info("execution:{}", execution);
            } catch (RepeatKillException re) {
                logger.error(re.getMessage());
            } catch (SeckillCloseException se) {
                logger.error(se.getMessage());
            }
        } else {
            logger.warn("exposer={}", exposer);
        }

        /**Exposer{exposed=true,
         *  md5='67148714f49bbdd9d53851a66cf53af6',
         *  seckillId=1004,
         *  now=0, start=0, end=0}
         *
         */
    }

    @Test
    public void testExecuteSeckill() throws Exception {
        //org.seckill.exception.RepeatKillException: seckill repeated
        long id = 1004;
        long phone = 13122204541L;
        String md5 = "67148714f49bbdd9d53851a66cf53af6";
        try {
            SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
            logger.info("execution:{}", execution);
        } catch (RepeatKillException re) {
            logger.error(re.getMessage());
        } catch (SeckillCloseException se) {
            logger.error(se.getMessage());
        }
    }

    @Test
    public void testGetSeckillList1() throws Exception {

    }

    @Test
    public void testGetById1() throws Exception {

    }

    @Test
    public void testExportSeckillUrl() throws Exception {

    }

    @Test
    public void testExecuteSeckill1() throws Exception {

    }

    @Test
    public void testExecuteSeckillProcedure() throws Exception {
        long seckilId = 1004;
        long phone = 1234567891;
        Exposer exposer = seckillService.exportSeckillUrl(seckilId);
        if(exposer.isExposed()){
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.executeSeckillProcedure(seckilId,phone,md5);
            logger.info(execution.getStateInfo());
        }
    }
}