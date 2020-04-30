package com.example.filehub.service.uaa.security.handler;

import com.example.filehub.commons.service.global.dto.BaseResult;
import com.example.filehub.commons.service.global.dto.factory.BaseResultFactory;
import com.example.filehub.commons.service.util.JsonUtil;
import com.example.filehub.service.uaa.security.entity.SecurityUser;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.filehub.commons.service.constant.MiscConstant.CONTENT_TYPE_JSON;

/**
 * @author yinfelix
 * @date 2020/4/16
 */
@Slf4j
@Component
public class UserAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info(authentication.getName() + "用户登录成功");
        String encodedToken = "";

        if (authentication instanceof OAuth2Authentication) {
            String authorization = request.getHeader("Authorization");
            if (authorization == null || !authorization.startsWith("Basic ")) {
                throw new UnapprovedClientAuthenticationException("请求头中无client信息");
            }
            String[] tokens = extractAndDecodeHeader(authorization, request);
            assert tokens.length == 2;
            String clientId = tokens[0];
            String clientSecret = tokens[1];

            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

            if (clientDetails == null) {
                throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
            } else if (!StringUtils.pathEquals(clientDetails.getClientSecret(), clientSecret)) {
                throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
            }

            TokenRequest tokenRequest = new TokenRequest(Maps.newHashMap(), clientId, clientDetails.getScope(), "custom");

            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

            OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        }

        /*
        普通认证，将Authentication对象持久化进入token
         */
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            List<String> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

//            OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
//            Map<String, String> requestParameters = oAuth2Request.getRequestParameters();
//            Map<String, Object> token = new HashMap<>(requestParameters);
            Map<String, Object> token = new HashMap<>();

            token.put("principal", authentication.getName());
            token.put("authorities", authorities);
            token.put("details", authentication.getDetails());

//            token.put("clientId", oAuth2Authentication.getOAuth2Request().getClientId());
//            OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) oAuth2Authentication.getDetails();
//            token.put("token_type", oAuth2AuthenticationDetails.getTokenType());
//            token.put("token", oAuth2AuthenticationDetails.getTokenValue());

            encodedToken = Base64Utils.encodeToString(JsonUtil.getJsonStringFromObjectIgnoresNull(token).getBytes());
        }

        SecurityUser securityUser = ((SecurityUser) authentication.getPrincipal());
        Map<String, Object> dataMap = new HashMap<>(3);
        dataMap.put("token", encodedToken);
        BaseResult successResult = BaseResultFactory.getSuccessResult(String.format("%s用户登录成功", securityUser.getUsername()), dataMap);

        response.setContentType(CONTENT_TYPE_JSON);
        response.setStatus(successResult.getStatusCode());

        try {
            PrintWriter writer = response.getWriter();
            writer.write(JsonUtil.getJsonStringFromObjectIgnoresNull(successResult));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error("UserAuthenticationSuccessHandler#onAuthenticationSuccess()", e);
        }
    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) {

        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64Utils.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
        String token = new String(decoded, StandardCharsets.UTF_8);
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }
}
