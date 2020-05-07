package com.example.filehub.service.uaa.auth.filter;

import com.example.filehub.service.uaa.auth.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.filehub.commons.constant.MiscConstant.JWT_KEY_BEARER;

/**
 * @deprecated 鉴权应在微服务中而非uaa中处理
 * @author yinfelix
 * @date 2020/3/9
 */
@Slf4j
//@Component
public class JwtVerificationFilter extends OncePerRequestFilter {
    @Qualifier(value = BeanIds.USER_DETAILS_SERVICE)
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("token");

        if (header != null && header.startsWith(JWT_KEY_BEARER)) {

            header = header.replaceFirst(JWT_KEY_BEARER, "");
            /*
            暂用统一公钥
             */
//            String subjectFromToken = JwtUtils.getSubjectFromToken(header, rsaKeyConfiguration.getPublicKey());
            if (!StringUtils.isEmpty(header)) {
                /*
                从jwt中重新获取UserDetails，最终放入应用上下文
                 */
                UserDetails userDetails = userDetailsService.loadUserByUsername(header);

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(header, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authResult);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
