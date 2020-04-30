package com.example.filehub.service.library.service.impl;

import com.example.filehub.commons.service.entity.File;
import com.example.filehub.commons.service.entity.Library;
import com.example.filehub.service.library.dao.LibraryMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.example.filehub.service.library.dao.FileMapper;
import com.example.filehub.service.library.service.FileService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService{
    @Resource
    private FileMapper fileMapper;
    @Resource
    private LibraryMapper libraryMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int upload(File file, Long fileUploaderUid, Long libraryId) {
//        OSS
        int flag = 0;
        final String fileDisplayName = file.getFileDisplayName();
        final String fileDesc = file.getFileDesc();
        final String fileType = file.getFileType();
        if (!StringUtils.isEmpty(fileDisplayName) && !StringUtils.isEmpty(fileType)) {
            final Library library = libraryMapper.selectByPrimaryKey(libraryId);
            final LocalDateTime uploadTime = LocalDateTime.now();
            file = new File(fileDisplayName, fileDesc, fileType, fileUploaderUid, uploadTime);

            List<Library> parentLibraries = file.getParentLibraries();
            if (parentLibraries == null || parentLibraries.isEmpty()) {
                parentLibraries = new ArrayList<>();
            }
            parentLibraries.add(library);
            file.setParentLibraries(parentLibraries);
        }
        flag = fileMapper.insertSelective(file);

        if (flag != 0) {
            flag = fileMapper.insertLibraryFileRelationship(libraryId, file.getFileId());
        }
        return flag;
    }

    @Transactional(readOnly = true)
    @Override
    public int download(String fileUrl, Long fileDownloaderUid) {
//        OSS
        return 0;
    }
}
