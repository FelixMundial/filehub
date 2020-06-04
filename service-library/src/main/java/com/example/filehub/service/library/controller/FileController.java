package com.example.filehub.service.library.controller;

import com.example.filehub.common.service.util.CommonSecurityUtil;
import com.example.filehub.commons.constant.MiscConstant;
import com.example.filehub.commons.entity.File;
import com.example.filehub.commons.entity.oss.OssObject;
import com.example.filehub.commons.global.dto.BaseResult;
import com.example.filehub.commons.global.dto.factory.BaseResultFactory;
import com.example.filehub.service.library.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author yinfelix
 * @date 2020/5/7
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping(value = "/", consumes = MiscConstant.CONTENT_TYPE_JSON)
    public BaseResult handleFileUploading(@RequestBody OssObject fileInfo) {
        File file = new File(
                /*fileInfo.getFileEtag(),*/
                fileInfo.getFileUrl(),
                fileInfo.getFileName(),
                fileInfo.getFileUrl(),
                fileInfo.getFileType(),
                Long.parseLong(fileInfo.getFileSize()),
                LocalDateTime.ofInstant(fileInfo.getFileLastModifiedDate().toInstant(), ZoneId.systemDefault())
        );

        int saveUploadedFileInfo = fileService.saveUploadedFileInfo(
                file,
                ((Integer) CommonSecurityUtil.getSecurityPrincipal().get("uid")).longValue(),
                fileInfo.getFileParentLibraryIds().get(0)
        );
        if (saveUploadedFileInfo == 0) {
            return BaseResultFactory.getFailureResult(HttpStatus.SERVICE_UNAVAILABLE.value(), "文件上传信息落库失败！");
        }

        log.debug("文件上传信息落库成功！");
        return BaseResultFactory.getSuccessResultWithMessage("文件上传信息落库成功！");
    }
}
