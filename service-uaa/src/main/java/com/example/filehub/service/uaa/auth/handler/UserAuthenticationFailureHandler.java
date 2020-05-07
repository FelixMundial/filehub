package com.example.filehub.service.uaa.auth.handler;

import com.example.filehub.commons.global.dto.BaseResult;
import com.example.filehub.commons.global.dto.factory.BaseResultFactory;
import com.example.filehub.commons.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.example.filehub.commons.constant.MiscConstant.CONTENT_TYPE_JSON;

/**
 * @author yinfelix
 * @date 2020/4/16
 */
@Slf4j
@Component
public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        BaseResult failureResult;

        if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
//            failureResult = BaseResultFactory.getFailureResult(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
            failureResult = BaseResultFactory.getFailureResult(HttpStatus.UNAUTHORIZED.value(), "用户名或密码错误！");
        } else if (exception instanceof LockedException) {
            failureResult = BaseResultFactory.getFailureResult(HttpStatus.LOCKED.value(), "账户被锁定，请联系管理员！");
        } else if (exception instanceof CredentialsExpiredException) {
            failureResult = BaseResultFactory.getFailureResult(HttpStatus.UNAUTHORIZED.value(), "证书过期，请联系管理员！");
        } else if (exception instanceof AccountExpiredException) {
            failureResult = BaseResultFactory.getFailureResult(HttpStatus.UNAUTHORIZED.value(), "账户过期，请联系管理员！");
        } else if (exception instanceof DisabledException) {
            failureResult = BaseResultFactory.getFailureResult(HttpStatus.FORBIDDEN.value(), "账户被禁用，请联系管理员！");
        } else {
            log.error("登录失败", exception);
            failureResult = BaseResultFactory.getFailureResult(HttpStatus.SERVICE_UNAVAILABLE.value(), "登录失败，请稍后重试...");
        }

        response.setContentType(CONTENT_TYPE_JSON);
        /*
        response响应应为200？
         */
        response.setStatus(HttpStatus.OK.value());
//        response.setStatus(failureResult.getStatusCode());

        try {
            PrintWriter writer = response.getWriter();
            writer.write(JsonUtil.getJsonStringFromObject(failureResult));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error("UserAuthenticationFailureHandler#onAuthenticationFailure()", e);
        }
    }
}
