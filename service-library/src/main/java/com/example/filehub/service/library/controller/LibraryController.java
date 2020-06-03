package com.example.filehub.service.library.controller;

import com.example.filehub.common.service.service.CommonSecurityUtil;
import com.example.filehub.commons.entity.Library;
import com.example.filehub.commons.global.dto.BaseResult;
import com.example.filehub.commons.global.dto.factory.BaseResultFactory;
import com.example.filehub.service.library.service.FileService;
import com.example.filehub.service.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import io.swagger.annotations.*;

/**
 * @author yinfelix
 * @date 2020/4/15
 */
@Api(value = "首页项目库展示")
//@CrossOrigin
@RestController
//@RequestMapping(value = "/library")
public class LibraryController {
    @Autowired
    private LibraryService libraryService;
    @Autowired
    private FileService fileService;

    @ApiImplicitParams(value = {})
    @ApiOperation("展示最热项目")
    @PreAuthorize("hasAnyAuthority('role_admin', 'role_user', 'role_guest')")
    @GetMapping("/top")
    public BaseResult displayTopLibraries() {
        return BaseResultFactory.getSuccessResultWithData(
                libraryService.findTopLibraries(0)
        );
    }

    @ApiImplicitParams(value = {})
    @ApiOperation("展示最新项目")
    @PreAuthorize("hasAnyAuthority('role_admin0', 'role_admin', 'role_vip', 'role_guest')")
    @GetMapping("/new")
    public BaseResult displayNewLibraries() {
        return BaseResultFactory.getSuccessResultWithData(
                libraryService.findNewLibraries(LocalDateTime.now())
        );
    }

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "uid", value = "用户ID", dataType = "java.lang.Long")})
    @ApiOperation("展示指定用户所有项目")
    @PreAuthorize("hasAnyAuthority('role_admin0', 'role_admin', 'role_vip')")
    @GetMapping("/user/{uid}")
    public BaseResult displayUserLibraries(@PathVariable("uid") Long uid) {
        return BaseResultFactory.getSuccessResultWithData(
                libraryService.findAllLibrariesByUser(uid, true)
        );
    }

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "libraryId", value = "项目ID", dataType = "java.lang.Long")})
    @ApiOperation("展示指定项目内所有文件")
    @PreAuthorize("hasAnyAuthority('role_admin0', 'role_admin', 'role_vip')")
    @GetMapping("/{libraryId}/files")
    public BaseResult displayFilesInLibrary(@PathVariable("libraryId") Long libraryId) {
        return BaseResultFactory.getSuccessResultWithData(
                fileService.findAllFilesByLibrary(libraryId)
        );
    }

    @ApiOperation("创建项目")
    @PreAuthorize("hasAnyAuthority('role_admin0', 'role_admin', 'role_vip')")
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public BaseResult createLibrary(@Validated @RequestBody Library library, BindingResult bindingResult) {
        if (bindingResult.hasErrors() && bindingResult.getFieldError() != null) {
            final String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return BaseResultFactory.getFailureResult(
                    HttpStatus.SERVICE_UNAVAILABLE.value(), String.format("%s项目创建失败：%s", library.getLibraryName(), errorMessage)
            );
        }

        /*
        创建前查询是否已存在
         */
        Library tempLibrary = libraryService.findLibraryByName(library.getLibraryName());
        if (tempLibrary == null) {
            tempLibrary = libraryService.createLibrary(library, (Long) CommonSecurityUtil.getSecurityPrincipal().get("uid"));
            if (tempLibrary != null) {
                return BaseResultFactory.getSuccessResultWithMessage(String.format("%s项目创建成功", library.getLibraryName()));
            }
        } else {
            return BaseResultFactory.getFailureResult(
                    HttpStatus.SERVICE_UNAVAILABLE.value(), String.format("%s项目创建失败：%s", library.getLibraryName(), "同名项目已存在！")
            );
        }

        return BaseResultFactory.getFailureResult(
                HttpStatus.SERVICE_UNAVAILABLE.value(), String.format("%s项目创建失败：%s", library.getLibraryName(), "请稍后重试")
        );
    }
}
