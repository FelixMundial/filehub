package com.example.filehub.commons.service.dto.factory;

import com.example.filehub.commons.service.dto.BaseResult;
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

    public static BaseResult getFailureResult(String message) {
        return getResult(HttpStatus.SERVICE_UNAVAILABLE.value(), message, null);
    }
    public static BaseResult getFailureResult(int httpStatus, String message) {
        return getResult(httpStatus, message, null);
    }
    public static BaseResult getFailureResult() {
        return getResult(HttpStatus.BAD_REQUEST.value(), "FAILURE", null);
    }

    public static BaseResult get404Result() {
        return getResult(HttpStatus.NOT_FOUND.value(), "Not found", null);
    }

    public static BaseResult getUnauthResult() {
        return getResult(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", null);
    }

    public static BaseResult getForbiddenResult() {
        return getResult(HttpStatus.FORBIDDEN.value(), "Forbidden", null);
    }

    public static BaseResult getTimeoutResult() {
        return getResult(HttpStatus.REQUEST_TIMEOUT.value(), "Request timeout", null);
    }

    public static BaseResult getResult(int statusCode, String message, Object data) {
        return baseResultDto(statusCode, message, data);
    }

    protected static BaseResult baseResultDto(int statusCode, String message, Object data) {
        return new BaseResult(statusCode, message, data);
    }
}
