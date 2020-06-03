package com.example.filehub.service.library.service.impl;

import com.example.filehub.commons.entity.File;
import com.example.filehub.commons.entity.Library;
import com.example.filehub.service.library.service.LibraryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import com.example.filehub.service.library.dao.LibraryMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {
    @Resource
    private LibraryMapper libraryMapper;

    @Override
    public Library findLibraryById(Long libraryId) {
        return libraryMapper.selectByPrimaryKey(libraryId);
    }

    @Override
    public Library findLibraryByName(String libraryName) {
        Library library = new Library();
        library.setLibraryName(libraryName);
        return libraryMapper.selectOne(library);
    }

    @Override
    public List<Library> findTopLibraries(Integer minFollowersCount) {
        return libraryMapper.findAllByFollowersCountGreaterThanOrEqualToOrderByFollowersCountDesc(minFollowersCount);
    }

    @Override
    public List<Library> findNewLibraries(LocalDateTime now) {
        return libraryMapper.findAllByLibraryCreationTimeBeforeOrderByLibraryCreationTimeDesc(now);
    }

    @Override
    public List<Library> findNewlyUpdatedLibraries(LocalDateTime now) {
        return libraryMapper.findAllByLibraryLastUpdateTimeBeforeOrderByLibraryLastUpdateTimeDesc(now);
    }

    @Override
    public List<Library> findAllHotLibrariesByUser(Long uid, boolean includePrivate) {
        if (includePrivate) {
            return libraryMapper.findAllByOwnerUidOrderByFollowersCountDesc(uid);
        }
        return libraryMapper.findAllByOwnerUidAndPrivacyTypeFalseOrderByFollowersCountDesc(uid);
    }

    @Override
    public List<Library> findAllNewLibrariesByUser(Long uid, boolean includePrivate) {
        if (includePrivate) {
            return libraryMapper.findAllByOwnerUidOrderByLibraryLastUpdateTimeDesc(uid);
        }
        return libraryMapper.findAllByOwnerUidAndPrivacyTypeFalseOrderByLibraryLastUpdateTimeDesc(uid);
    }

    @Override
    public List<Library> findAllLibrariesByUser(Long uid, boolean includePrivate) {
        if (includePrivate) {
            return libraryMapper.findAllByOwnerUidOrderByLibraryName(uid);
        }
        return libraryMapper.findAllByOwnerUidAndPrivacyTypeFalseOrderByLibraryName(uid);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Library createLibrary(Library library, Long creatorUid) {
        @NotNull(message = "文档名不能为空") final String libraryName = library.getLibraryName();
        final String libraryDesc = library.getLibraryDesc();
        if (!StringUtils.isEmpty(libraryName)) {
            final LocalDateTime creationTime = LocalDateTime.now();
            library = new Library(libraryName, libraryDesc, creatorUid, creationTime);
            int mapperFlag = libraryMapper.insertSelective(library);
            if (mapperFlag != 0) {
                mapperFlag = libraryMapper.insertLibraryCollaboratorRelationship(library.getLibraryId(), creatorUid);
                /*
                若插入关联表失败？
                 */
//                if (mapperFlag == 0) {
//                    throw new RuntimeException("文件上传信息插入失败！");
//                }
            }
            if (mapperFlag != 0) {
                return library;
            }
        }
        return null;
    }
}

