package com.example.filehub.service.uaa.controller;

import com.example.filehub.commons.service.dto.BaseResult;
import com.example.filehub.commons.service.dto.factory.BaseResultFactory;
import com.example.filehub.commons.service.entity.UserAccountInfo;
import com.example.filehub.service.uaa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @deprecated 认证/登录流程全部由Spring Security接管，不再手动通过Controller进行
 * @author yinfelix
 * @date 2020/4/14
 */
@Slf4j
@RestController
public class UserLoginController {
    @Autowired
    private UserService userService;

//    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public BaseResult login(@Validated @RequestBody UserAccountInfo userAccountInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors() && bindingResult.getFieldError() != null) {
            final String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return BaseResultFactory.getFailureResult(
                    HttpStatus.SERVICE_UNAVAILABLE.value(), String.format("%s", errorMessage)
            );
        }

        final UserAccountInfo user = userService.findUserByLoginNameAndLoginPassword(userAccountInfo.getUserLoginName(), userAccountInfo.getUserLoginPassword());
        if (user != null) {
            return BaseResultFactory.getSuccessResult(String.format("%s用户登录成功", userAccountInfo.getUserLoginName()));
        }
        return BaseResultFactory.getFailureResult(
                HttpStatus.SERVICE_UNAVAILABLE.value(), String.format("%s用户登录失败：%s", userAccountInfo.getUserLoginName(), "用户名或密码错误")
        );
    }

//    @GetMapping("/all")
//    public List<User> findAllUsers() {
//        return userService.findAllUsers();
//    }

//    @GetMapping("/{emplId}")
//    public List<User> findByUserLoginNameLike(@PathVariable("userLoginName") String userLoginName) {
//        log.info("findByUserLoginNameLike()：{}", userLoginName);
//        List<User> users = userService.findByUserLoginNameAndLoginPassword("%" + userLoginName + "%");
//
//        return new ArrayList<>(users);
//    }
}
