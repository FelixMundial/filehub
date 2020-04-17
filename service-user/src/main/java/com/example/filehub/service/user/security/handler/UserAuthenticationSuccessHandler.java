package com.example.filehub.service.user.security.handler;

import com.example.filehub.commons.service.dto.BaseResult;
import com.example.filehub.commons.service.dto.factory.BaseResultFactory;
import com.example.filehub.commons.service.util.JsonUtils;
import com.example.filehub.commons.service.util.JwtUtils;
import com.example.filehub.service.user.security.config.RsaKeyConfiguration;
import com.example.filehub.service.user.security.entity.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.example.filehub.commons.service.util.Constants.HEADER_KEY_AUTHORIZATION;
import static com.example.filehub.commons.service.util.Constants.CONTENT_TYPE_JSON;

/**
 * @author yinfelix
 * @date 2020/4/16
 */
@Slf4j
@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private RsaKeyConfiguration properties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info(authentication.getName() + "用户登录成功");
        String token;

        /*
        获取SecurityUser对象（authentication对象已去除credentials信息），并向请求体中写入token
        暂用统一私钥
         */
        SecurityUser securityUser = ((SecurityUser) authentication.getPrincipal());
        token = JwtUtils.generateToken(securityUser, properties.getPrivateKey(), 60 * 24);
        response.addHeader(HEADER_KEY_AUTHORIZATION, token);
        BaseResult successResult = BaseResultFactory.getSuccessResult(String.format("%s用户登录成功", securityUser.getUsername()));

        response.setContentType(CONTENT_TYPE_JSON);
        response.setStatus(successResult.getStatusCode());

        try {
            PrintWriter writer = response.getWriter();
            writer.write(JsonUtils.getJsonStringFromObject(successResult));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error("向前端输出JSON发生异常", e);
        }
    }
}
