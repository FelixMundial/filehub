package com.example.filehub.service.user.security.filter;

import com.example.filehub.commons.service.util.JwtUtils;
import com.example.filehub.service.user.security.config.RsaKeyConfiguration;
import com.example.filehub.service.user.security.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.filehub.commons.service.util.Constants.JWT_KEY_BEARER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author yinfelix
 * @date 2020/3/9
 */
@Slf4j
@Component
public class JwtVerificationFilter extends BasicAuthenticationFilter {

    @Autowired
    private RsaKeyConfiguration rsaKeyConfiguration;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public JwtVerificationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(AUTHORIZATION);

        if (header != null && header.startsWith(JWT_KEY_BEARER)) {

            header = header.replaceFirst(JWT_KEY_BEARER, "");
            /*
            暂用统一公钥
             */
            String subjectFromToken = JwtUtils.getSubjectFromToken(header, rsaKeyConfiguration.getPublicKey());
            if (!StringUtils.isEmpty(subjectFromToken)) {
                /*
                从jwt中重新获取UserDetails
                 */
                UserDetails userDetails = userDetailsService.loadUserByUsername(subjectFromToken);

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(subjectFromToken, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authResult);
                }
            }
        }

        /*
        该类是过滤器链中执行靠近中间的类，不能进行响应输出行为
         */
        chain.doFilter(request, response);
    }
}
