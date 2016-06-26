package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by dingyunxiang on 16/6/25.
 */


@Service
public class SeckillServceImpl implements SeckillService {
    //用于混淆md5
    private final String slat = "sadjhsajd*sdh6^sgad2293#$";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 100);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date now = new Date();
        if (now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, now.getTime(), startTime.getTime(), endTime.getTime());

        }
        //转化特定字符串的过程,不可逆
        String md5 = getMD5(seckillId);//TODO
        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 使用注解控制事务方法的优点:
     * 1.开发团队达成一致约定,明确事务标注的编程风格.
     * 2.保证事务方法的执行时间尽可能短,不要穿插其他的网络操作(RPC/HTTP请求,或者剥离到事务方法外)
     * 3.不是所有的方法都需要事务,例如:只有一条修改操作,只读操作不需要事务控制
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillCloseException, RepeatKillException, SeckillException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillCloseException("seckill data rewrite");
        }
        //执行秒杀逻辑:减库存 + 记录购买行为
        Date now = new Date();
        try {
            //减库存
            int updateCount = seckillDao.reduceNumber(seckillId, now);
            if (updateCount <= 0) {
                //没有更新到记录,秒杀结束
                throw new SeckillCloseException("seckill is closed");
            } else {
                //记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                //唯一验证:seckillId,userPhone
                if (insertCount <= 0) {
                    //重复秒杀
                    throw new RepeatKillException("seckill repeated");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException sc) {
            throw sc;
        } catch (RepeatKillException ee) {
            throw ee;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所有编译器异常 转化为运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }
}
