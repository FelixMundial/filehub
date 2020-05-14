package com.example.filehub.commons.global.resolver;

import com.example.filehub.commons.global.dto.factory.BaseResultFactory;
import com.example.filehub.commons.global.dto.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author yinfelix
 * @apiNote 全局异常处理
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
        log.error("handleUnexpectedException()", e);
        return BaseResultFactory.getFailureResult(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
    }
}
