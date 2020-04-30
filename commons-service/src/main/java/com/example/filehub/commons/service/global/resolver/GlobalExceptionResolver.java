package com.example.filehub.commons.service.global.resolver;

import com.example.filehub.commons.service.global.dto.BaseResult;
import com.example.filehub.commons.service.global.dto.factory.BaseResultFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @apiNote 全局异常处理
 * @author yinfelix
 * @date 2020/4/28
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionResolver {
    /**
     * 处理所有不可知异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResult handleUnexpectedException(Exception e) {
        log.error(e.getMessage(), e);
        return BaseResultFactory.getFailureResult(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
    }

    /**
     * 处理所有业务异常
     */
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public BaseResult handleOpdRuntimeException(Exception e) {
//        log.error(e.getMessage(), e);
//        return BaseResultFactory.getFailureResult(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
//    }
}
