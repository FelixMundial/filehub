package com.example.filehub.service.gateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yinfelix
 * @date 2020/4/28
 * @deprecated
 */
@Slf4j
@Component
public class GatewayFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE; // 可以在请求被路由之前调用
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        /*
        即使在网关解析token放入安全上下文，资源服务器OncePerRequestFilter中仍无法直接获取Authentication对象
         */
//        final ObjectMapper mapper = new ObjectMapper();
//
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//        HttpServletResponse response = ctx.getResponse();
//        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (!StringUtils.isEmpty(token)) {
//            token = new String(Base64Utils.decodeFromString(token));
//            JsonNode jsonNode = mapper.readTree(token);
//
//            /*
//            还原用户信息（除密码），最终以UsernamePasswordAuthenticationToken的形式放入上下文
//             */
//            String principal = jsonNode.get("principal").asText();
////            UserAccountInfo userInfo = mapper.readValue(principal, UserAccountInfo.class);
//
//            List<GrantedAuthority> authorities = new ArrayList<>();
//            jsonNode.get("authorities").forEach(singleJsonNode -> {
//                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(singleJsonNode.asText());
//                authorities.add(simpleGrantedAuthority);
//            });
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                    principal, null, authorities
//            );
////            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
////                    userInfo, null, authorities
////            );
//            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//            /*
//            将新的UsernamePasswordAuthenticationToken放入应用上下文
//             */
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            log.debug(authenticationToken.toString() + " added to SecurityContext");
//        }

        /*
        @deprecated 暂时无法解决网关跨域问题（无法变更执行顺序）
         */
//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, OPTIONS, PATCH");
//        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type");
//        response.setHeader("Access-Control-Expose-Headers", "X-forwared-port, X-forwarded-host");
//        response.setHeader("Vary", "Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
//
//        // 非简单跨域请求一共进行两次，先发送OPTIONS请求以验证实际请求是否可以被接受
//        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
//            ctx.setSendZuulResponse(false); //验证请求不进行路由转发
//            ctx.setResponseStatusCode(HttpStatus.OK.value()); //返回验证成功的状态码
//            return null;
//        }
//        // 第二次请求（实际请求）
//        // 不需要认证token
//        ctx.setSendZuulResponse(true); //对请求进行路由
//        ctx.setResponseStatusCode(HttpStatus.OK.value());

        return null;
    }
}
