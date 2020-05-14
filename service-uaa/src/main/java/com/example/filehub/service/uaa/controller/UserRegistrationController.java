package com.example.filehub.service.uaa.controller;

import com.example.filehub.commons.global.dto.BaseResult;
import com.example.filehub.commons.global.dto.factory.BaseResultFactory;
import com.example.filehub.commons.entity.user.UserAccountInfo;
import com.example.filehub.service.uaa.service.UserService;
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

        /*
        插入前查询是否已存在
         */
        final UserAccountInfo accountInfo = userService.findUserByLoginName(userAccountInfo.getUserLoginName());
        if (accountInfo == null) {
            final UserAccountInfo savedUser = userService.saveUser(userAccountInfo);
            if (savedUser != null) {
                return BaseResultFactory.getSuccessResultWithMessage(String.format("%s用户注册成功", userAccountInfo.getUserLoginName()));
            }
        } else {
            return BaseResultFactory.getFailureResult(
                    HttpStatus.IM_USED.value(), String.format("%s用户注册失败：%s", userAccountInfo.getUserLoginName(), "该用户已被注册！")
            );
        }

        return BaseResultFactory.getFailureResult(
                HttpStatus.SERVICE_UNAVAILABLE.value(), String.format("%s用户注册失败：%s", userAccountInfo.getUserLoginName(), "请稍后重试")
        );
    }
}
