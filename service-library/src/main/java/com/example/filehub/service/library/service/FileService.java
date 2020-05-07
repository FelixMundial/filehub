package com.example.filehub.service.library.service;

import com.example.filehub.commons.entity.File;

public interface FileService {
    /**
     * 将已上传oss的文件信息落库（若发生异常，需通过定时任务进行重试）
     *
     * @param file            已上传的文件
     * @param fileUploaderUid 上传人uid
     * @param libraryId       所在library
     * @return 是否落库成功
     */
    int saveUploadedFileInfo(File file, Long fileUploaderUid, Long libraryId);
}
