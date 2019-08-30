package com.cw.faquir.core.exception.custom;

/**
 * 系统异常类, 系统出现错误, 抛出的异常
 * @author Lao SiCheng
 * @version 0.1
 */
public class SystemException extends Exception{
    public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }
}
