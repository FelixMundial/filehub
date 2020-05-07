package com.example.filehub.service.library.controller;

import com.example.filehub.commons.global.dto.BaseResult;
import com.example.filehub.commons.global.dto.factory.BaseResultFactory;
import com.example.filehub.service.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class LibraryDisplayController {
    @Autowired
    private LibraryService libraryService;

    @ApiImplicitParams(value = {})
    @ApiOperation("展示最热项目")
    @PreAuthorize("hasAnyAuthority('role_admin', 'role_user', 'role_guest')")
    @GetMapping("/explore/top")
    public BaseResult displayTopLibraries() {
        return BaseResultFactory.getSuccessResult(
                libraryService.findTopLibraries(0)
        );
    }

    @ApiImplicitParams(value = {})
    @ApiOperation("展示最新项目")
    @PreAuthorize("hasAnyAuthority('role_admin0', 'role_admin', 'role_vip', 'role_guest')")
    @GetMapping("/explore/new")
    public BaseResult displayNewLibraries() {
        return BaseResultFactory.getSuccessResult(
                libraryService.findNewLibraries(LocalDateTime.now())
        );
    }

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "uid", value = "用户ID", dataType = "java.lang.Long")})
    @ApiOperation("展示指定用户所有项目")
    @PreAuthorize("hasAnyAuthority('role_admin0', 'role_admin', 'role_vip')")
    @GetMapping("/user/{uid}")
    public BaseResult displayUserLibraries(@PathVariable("uid") Long uid) {
        return BaseResultFactory.getSuccessResult(
                libraryService.findAllLibrariesByUser(uid, true)
        );
    }

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "libraryId", value = "项目ID", dataType = "java.lang.Long")})
    @ApiOperation("展示指定项目内所有文件")
    @PreAuthorize("hasAnyAuthority('role_admin0', 'role_admin', 'role_vip')")
    @GetMapping("/{libraryId}/files")
    public BaseResult displayFilesInLibrary(@PathVariable("libraryId") Long libraryId) {
        return BaseResultFactory.getSuccessResult(
                libraryService.findAllFilesByLibrary(libraryId)
        );
    }
}
