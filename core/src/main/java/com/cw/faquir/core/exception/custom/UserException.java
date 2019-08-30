package com.cw.faquir.core.exception.custom;

/**
 * 用户错误异常类
 * 用户用户行为错误抛出的异常
 * @author Lao SiCheng
 * @version 0.1
 */
public class UserException extends Exception {
    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }
}
