package com.example.filehub.service.library.dao;
import java.time.LocalDateTime;

import com.example.filehub.commons.service.entity.File;
import org.apache.ibatis.annotations.Param;

import com.example.filehub.commons.service.entity.Library;
import com.example.filehub.service.library.dao.base.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LibraryMapper extends TkBaseMapper<Library> {
    List<Library> findAllByFollowersCountGreaterThanOrEqualToOrderByFollowersCountDesc(@Param("minFollowersCount")Integer minFollowersCount);

    List<Library> findAllByLibraryCreationTimeBeforeOrderByLibraryCreationTimeDesc(@Param("maxLibraryCreationTime")LocalDateTime maxLibraryCreationTime);

    List<Library> findAllByLibraryLastUpdateTimeBeforeOrderByLibraryLastUpdateTimeDesc(@Param("maxLibraryLastUpdateTime")LocalDateTime maxLibraryLastUpdateTime);

    List<Library> findAllByOwnerUidOrderByFollowersCountDesc(@Param("ownerUid") Long ownerUid);
    List<Library> findAllByOwnerUidAndPrivacyTypeFalseOrderByFollowersCountDesc(@Param("ownerUid") Long ownerUid);

	List<Library> findAllByOwnerUidOrderByLibraryLastUpdateTimeDesc(@Param("ownerUid") Long ownerUid);
    List<Library> findAllByOwnerUidAndPrivacyTypeFalseOrderByLibraryLastUpdateTimeDesc(@Param("ownerUid") Long ownerUid);

	List<Library> findAllByOwnerUidOrderByLibraryName(@Param("ownerUid") Long ownerUid);
    List<Library> findAllByOwnerUidAndPrivacyTypeFalseOrderByLibraryName(@Param("ownerUid")Long ownerUid);

    List<File> findAllFilesByLibraryId(@Param("libraryId") Long libraryId);
}