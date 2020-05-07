package com.example.filehub.commons.global.dto.factory;

import com.example.filehub.commons.global.dto.BaseResult;
import org.springframework.http.HttpStatus;

/**
 * @author yinfelix
 * @date 2020/4/14
 */
public class BaseResultFactory {
    public static BaseResult getSuccessResult(String message, Object data) {
        return getResult(HttpStatus.OK.value(), message, data);
    }

    public static BaseResult getSuccessResult(Object data) {
        return getResult(HttpStatus.OK.value(), "OK", data);
    }

    public static BaseResult getSuccessResult(String message) {
        return getResult(HttpStatus.OK.value(), message, null);
    }

    public static BaseResult getFailureResult(int statusCode, String message) {
        return getResult(statusCode, message, null);
    }

    public static BaseResult getResult(int statusCode, String message, Object data) {
        return baseResultDto(statusCode, message, data);
    }

    protected static BaseResult baseResultDto(int statusCode, String message, Object data) {
        return new BaseResult(statusCode, message, data);
    }
}
