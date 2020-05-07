package com.example.filehub.common.service.filter;

import com.example.filehub.commons.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yinfelix
 * @apiNote 由token获取Authentication对象，封装为UsernamePasswordAuthenticationToken对象保存进安全上下文
 * @date 2020/4/24
 */
@Slf4j
@Component
public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        /*
        无法在资源服务器过滤器中直接获取Authentication，只能通过token获取
         */
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(LocalTime.now() + ": " + authentication.getPrincipal());

        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        /*
        验证缓存中的token是否过期（未实现）
         */
        if (!StringUtils.isEmpty(token) &&
                !token.equalsIgnoreCase("e")) {
            token = new String(Base64Utils.decodeFromString(token));
            /*
            直接将json转为map，而不再逐层解析json
             */
//            JsonNode jsonNode = mapper.readTree(token);
            final Map tokenMap = JsonUtil.getPojoFromJsonString(token, Map.class);

            List<GrantedAuthority> authorities = new ArrayList<>();
            ((List<String>) tokenMap.get("authorities")).forEach(authority -> {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
                authorities.add(simpleGrantedAuthority);
            });
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    tokenMap.get("principal"), null, authorities
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

            /*
            将重新构造的UsernamePasswordAuthenticationToken放入应用上下文
             */
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.debug(authenticationToken.toString() + " added to SecurityContext");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
