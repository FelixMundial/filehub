package com.example.filehub.service.library.service.impl;

import com.example.filehub.commons.entity.File;
import com.example.filehub.commons.entity.Library;
import com.example.filehub.service.library.dao.FileMapper;
import com.example.filehub.service.library.dao.LibraryMapper;
import com.example.filehub.service.library.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FileServiceImpl implements FileService{
    @Resource
    private FileMapper fileMapper;
    @Resource
    private LibraryMapper libraryMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveUploadedFileInfo(File file, Long fileUploaderUid, Long libraryId) {
        int mapperFlag;
        if (!StringUtils.isEmpty(file.getFileDisplayName()) && !StringUtils.isEmpty(file.getFileUrl())) {
            final Library library = libraryMapper.selectByPrimaryKey(libraryId);
            if (file.getFileCreationUid() == null) {
                file.setFileCreationUid(fileUploaderUid);
            }
            file.setFileLastUpdateUid(fileUploaderUid);

            List<Library> parentLibraries = file.getParentLibraries();
            if (parentLibraries == null || parentLibraries.isEmpty()) {
                parentLibraries = new ArrayList<>();
            }
            parentLibraries.add(library);
            file.setParentLibraries(parentLibraries);
        }
        log.debug("Saving file: {} in library {}", file, libraryId);
        mapperFlag = fileMapper.insertSelective(file);

        if (mapperFlag != 0) {
            mapperFlag = fileMapper.insertLibraryFileRelationship(libraryId, file.getFileId());
        }
        return mapperFlag;
    }
}
