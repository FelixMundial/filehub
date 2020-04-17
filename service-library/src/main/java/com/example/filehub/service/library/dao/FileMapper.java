package com.example.filehub.service.library.dao;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.example.filehub.commons.service.entity.File;
import com.example.filehub.service.library.dao.base.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends TkBaseMapper<File> {
    int insertLibraryFileRelationship(@Param("libraryId") Long libraryId, @Param("fileId") Long fileId);

    List<File> findAllFiles();
}