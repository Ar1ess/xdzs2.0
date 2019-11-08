package com.softlab.customer.util;

import com.softlab.common.RestData;
import com.softlab.common.exception.AppException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LiXiwen
 * @date 2019/11/8 12:11
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 自定义异常处理类
     * @param request
     * @param ex
     * @param response
     * @return
     */
    @ExceptionHandler(AppException.class)
    public RestData myHosException(HttpServletRequest request, final Exception ex,
                                   HttpServletResponse response) {
        //response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        System.out.println("AppException");
        logger.info("AppException");
        AppException e = (AppException) ex;
        //ex.printStackTrace();
        return new RestData(e.getCode(), e.getMessage());
    }

    /**
     * 全局异常处理类
     * @param request
     * @param ex
     * @param response
     * @return
     */
    @ExceptionHandler(Exception.class)
    public RestData runtimeHosException(HttpServletRequest request, final Exception ex,
                                        HttpServletResponse response) {
        //response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        System.out.println("AppException");
        logger.info("runtimeHosException");
        RuntimeException e = (RuntimeException) ex;
        //ex.printStackTrace();
        return new RestData(1, ex.getLocalizedMessage());
    }

}
