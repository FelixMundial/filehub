package com.example.filehub.service.user.security.handler;

import com.example.filehub.commons.service.dto.BaseResult;
import com.example.filehub.commons.service.dto.factory.BaseResultFactory;
import com.example.filehub.commons.service.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.example.filehub.commons.service.util.Constants.CONTENT_TYPE_JSON;

/**
 * @author yinfelix
 * @date 2020/4/16
 */
@Slf4j
@Component
public class UserAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        BaseResult failureResult = BaseResultFactory.getFailureResult(HttpStatus.FORBIDDEN.value(), "账户权限不足！");

        response.setContentType(CONTENT_TYPE_JSON);
        response.setStatus(failureResult.getStatusCode());

        try {
            PrintWriter writer = response.getWriter();
            writer.write(JsonUtils.getJsonStringFromObject(failureResult));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error("向前端输出JSON发生异常", e);
        }
    }
}
