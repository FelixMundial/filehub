package com.example.filehub.common.service.handler;

import com.example.filehub.commons.global.dto.BaseResult;
import com.example.filehub.commons.global.dto.factory.BaseResultFactory;
import com.example.filehub.commons.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.example.filehub.commons.constant.MiscConstant.CONTENT_TYPE_JSON;

/**
 * @author yinfelix
 * @date 2020/4/30
 */
@Slf4j
@Component
public class UserAuthenticationFailureEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        BaseResult failureResult;

        if (authException instanceof InsufficientAuthenticationException) {
            failureResult = BaseResultFactory.getFailureResult(HttpStatus.UNAUTHORIZED.value(), "请重新登录！");
        } else {
            failureResult = BaseResultFactory.getFailureResult(HttpStatus.UNAUTHORIZED.value(), "请登录！");
        }

        response.setContentType(CONTENT_TYPE_JSON);
        /*
        response响应应为200？
         */
        response.setStatus(HttpStatus.OK.value());

        try {
            PrintWriter writer = response.getWriter();
            writer.write(JsonUtil.getJsonStringFromObject(failureResult));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error("UserAuthenticationFailureEntryPoint#commence()", e);
        }
    }
}
