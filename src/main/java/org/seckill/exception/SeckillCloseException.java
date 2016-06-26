package org.seckill.exception;

/**
 * 秒杀关闭异常
 * Created by dingyunxiang on 16/6/25.
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
