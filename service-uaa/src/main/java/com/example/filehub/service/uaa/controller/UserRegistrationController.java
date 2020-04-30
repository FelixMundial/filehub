package com.example.filehub.service.uaa.controller;

import com.example.filehub.commons.service.global.dto.BaseResult;
import com.example.filehub.commons.service.global.dto.factory.BaseResultFactory;
import com.example.filehub.commons.service.entity.user.UserAccountInfo;
import com.example.filehub.commons.service.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author yinfelix
 * @date 2020/4/14
 */
@Slf4j
@RestController
public class UserRegistrationController {
    @Autowired
    private UserService userService;

//    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public BaseResult login(@Validated @RequestBody UserAccountInfo userAccountInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors() && bindingResult.getFieldError() != null) {
            final String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return BaseResultFactory.getFailureResult(
                    HttpStatus.SERVICE_UNAVAILABLE.value(), String.format("%s用户注册失败：%s", userAccountInfo.getUserLoginName(), errorMessage)
            );
        }

        final UserAccountInfo savedUser = userService.saveUser(userAccountInfo);
        if (savedUser != null) {
            return BaseResultFactory.getSuccessResult(String.format("%s用户注册成功", userAccountInfo.getUserLoginName()));
        }
        return BaseResultFactory.getFailureResult(
                HttpStatus.SERVICE_UNAVAILABLE.value(), String.format("%s用户注册失败：%s", userAccountInfo.getUserLoginName(), "请稍后重试")
        );
    }
}
