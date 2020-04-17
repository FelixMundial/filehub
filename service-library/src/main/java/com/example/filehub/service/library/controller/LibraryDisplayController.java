package com.example.filehub.service.library.controller;

import com.example.filehub.commons.service.entity.File;
import com.example.filehub.commons.service.entity.Library;
import com.example.filehub.service.library.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.*;

/**
 * @author yinfelix
 * @date 2020/4/15
 */
@Api(value = "首页项目库展示")
@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/libraries")
public class LibraryDisplayController {
    @Autowired
    LibraryService libraryService;

    @ApiImplicitParams(value = {})
    @ApiOperation("展示最热项目")
    @GetMapping("/explore/top")
    public List<Library> displayTopLibraries() {
        return libraryService.findTopLibraries(0);
    }

    @ApiImplicitParams(value = {})
    @ApiOperation("展示最新项目")
    @GetMapping("/explore/new")
    public List<Library> displayNewLibraries() {
        return libraryService.findNewLibraries(LocalDateTime.now());
    }

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "uid", value = "", dataType = "java.lang.Long")})
    @ApiOperation("展示指定用户所有项目")
    @GetMapping("/{uid}")
    public List<Library> displayUserLibraries(@PathVariable("uid") Long uid) {
        return libraryService.findAllLibrariesByUser(uid, true);
    }

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "libraryId", value = "", dataType = "java.lang.Long")})
    @ApiOperation("展示指定项目内所有文件")
    @GetMapping("/library/{libraryId}")
    public List<File> displayFilesInLibrary(@PathVariable("libraryId") Long libraryId) {
        return libraryService.findAllFilesByLibrary(libraryId);
    }
}
