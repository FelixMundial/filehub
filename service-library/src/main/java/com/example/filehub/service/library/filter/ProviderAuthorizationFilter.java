package com.example.filehub.service.library.filter;

import com.fasterxml.jackson.databind.JsonNode;
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

/**
 * @apiNote 由token获取Authentication对象，封装为UsernamePasswordAuthenticationToken对象保存进安全上下文
 * @author yinfelix
 * @date 2020/4/24
 */
@Slf4j
@Component
public class ProviderAuthorizationFilter extends OncePerRequestFilter {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        /*
        无法直接获取Authentication，须通过token获取
         */
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(LocalTime.now() + ": " + authentication.getPrincipal());

        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.isEmpty(token)) {
            token = new String(Base64Utils.decodeFromString(token));
            JsonNode jsonNode = mapper.readTree(token);

            /*
            还原用户信息（除密码），最终以UsernamePasswordAuthenticationToken的形式放入上下文
             */
            String principal = jsonNode.get("principal").asText();
//            UserAccountInfo userInfo = mapper.readValue(principal, UserAccountInfo.class);

            List<GrantedAuthority> authorities = new ArrayList<>();
            jsonNode.get("authorities").forEach(singleJsonNode -> {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(singleJsonNode.asText());
                authorities.add(simpleGrantedAuthority);
            });
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    principal, null, authorities
            );
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                    userInfo, null, authorities
//            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

            /*
            将新的UsernamePasswordAuthenticationToken放入应用上下文
             */
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.debug(authenticationToken.toString() + " added to SecurityContext");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
