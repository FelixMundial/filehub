package com.example.filehub.service.uaa.controller;

import com.example.filehub.commons.entity.user.Role;
import com.example.filehub.commons.global.dto.BaseResult;
import com.example.filehub.commons.global.dto.factory.BaseResultFactory;
import com.example.filehub.service.uaa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yinfelix
 * @date 2020/5/8
 * @deprecated uaa不是资源服务，故暂时不允许调用
 */
@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/query")
    public BaseResult getUserByUid(@RequestParam Long uid) {
        return BaseResultFactory.getSuccessResult(userService.findUserById(uid));
    }
}
