package com.example.filehub.common.service.util;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinfelix
 * @date 2020/6/3
 */
public class CommonSecurityUtil {
    public static Map getSecurityPrincipal() {
        return (HashMap) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
