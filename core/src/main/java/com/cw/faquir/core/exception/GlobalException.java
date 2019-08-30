package com.cw.faquir.core.exception;

import com.cw.faquir.core.domain.pojo.Result;
import com.cw.faquir.core.exception.custom.SystemException;
import com.cw.faquir.core.exception.custom.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常控制器
 * 负责系统所有异常捕捉, 统一风格返回
 * @author Lao SiCheng
 * @version 0.1
 */
@ControllerAdvice
@Slf4j
public class GlobalException {

    /**
     * 捕获 UserException 用户异常类
     * @param exception 异常参数
     * @return 返回格式信息
     */
    @ExceptionHandler(value = UserException.class)
    @ResponseBody
    public Result returnUserExceptionResult(Exception exception){
        Result result = new Result();
        result.setMessage(exception.getMessage());
        return result;
    }

    /**
     * 捕获 UserException 系统异常类
     * @param exception 异常参数
     * @return 返回格式信息
     */
    @ExceptionHandler(value = SystemException.class)
    @ResponseBody
    public Result returnSystemExceptionResult(Exception exception){
        Result result = new Result();
        result.setMessage(exception.getMessage());
        // todo 如何报告异常, email , sms
        log.error("系统出现异常{}", exception.getMessage());
        return result;
    }
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Result returnRuntimeExceptionResult(RuntimeException exception){
        Result result = new Result();
        result.setMessage(exception.getMessage());
        exception.printStackTrace();
        // todo 如何报告异常, email , sms
        log.error("系统出现异常{}", exception.getMessage());
        return result;
    }
}
