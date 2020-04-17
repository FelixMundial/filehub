package com.example.filehub.service.library.service;

import com.example.filehub.commons.service.entity.File;

public interface FileService{
    int upload(File file, Long fileUploaderUid, Long libraryId);

    int download(String fileUrl, Long fileDownloaderUid);
}
