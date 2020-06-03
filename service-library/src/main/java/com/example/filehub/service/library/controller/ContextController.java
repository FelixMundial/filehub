package com.example.filehub.service.library.controller;

import com.example.filehub.common.service.service.CommonSecurityUtil;
import com.example.filehub.commons.global.dto.BaseResult;
import com.example.filehub.commons.global.dto.factory.BaseResultFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author yinfelix
 * @date 2020/6/3
 */
@RestController
@RequestMapping("/context")
public class ContextController {
    @GetMapping("/me")
    public BaseResult getCurrentUser() {
        Map principal = CommonSecurityUtil.getSecurityPrincipal();
        if (principal != null) {
            return BaseResultFactory.getSuccessResultWithData(principal);
        }
        return BaseResultFactory.getFailureResult(HttpStatus.UNAUTHORIZED.value(), "未能获取登录信息，请检查是否已登录！");
    }
}
