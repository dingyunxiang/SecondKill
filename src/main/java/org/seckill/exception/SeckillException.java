package org.seckill.exception;

/**
 * 秒杀相关异常
 * Created by dingyunxiang on 16/6/25.
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
