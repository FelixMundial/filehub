package com.example.filehub.service.library;

import com.example.filehub.commons.entity.File;
import com.example.filehub.commons.entity.Library;
import com.example.filehub.service.library.dao.FileMapper;
import com.example.filehub.service.library.service.FileService;
import com.example.filehub.service.library.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@SpringBootTest
class JpaLibraryServiceApplicationTests {
    @Autowired
    LibraryService libraryService;
    @Autowired
    FileService fileService;

    @Test
    void findTopLibraries() {
        final List<Library> topLibraries = libraryService.findTopLibraries(0);
        topLibraries.forEach(System.out::println);
    }

    @Test
    void findNewLibraries() {
        List<Library> newLibraries = libraryService.findNewLibraries(LocalDateTime.now());
        newLibraries.forEach(System.out::println);
        List<Library> newlyUpdatedLibraries = libraryService.findNewlyUpdatedLibraries(LocalDateTime.now());
        newlyUpdatedLibraries.forEach(System.out::println);
    }

    @Test
    void findAllHotLibrariesByUser() {
        List<Library> libraries = libraryService.findAllHotLibrariesByUser(2L, true);
        libraries.forEach(System.out::println);
        libraries = libraryService.findAllHotLibrariesByUser(2L, false);
        libraries.forEach(System.out::println);
    }

    @Test
    void findAllNewLibrariesByUser() {
        final List<Library> libraries = libraryService.findAllNewLibrariesByUser(2L, true);
        libraries.forEach(System.out::println);
    }

    @Test
    void findAllFilesByLibrary() {
        final List<File> files = libraryService.findAllFilesByLibrary(3L);
        files.forEach(System.out::println);
    }

    @Rollback
    @Test
    void saveFilesInLibrary() {
        fileService.saveUploadedFileInfo(File.createNewFile("Gone with the Wind.txt", "", "text"), 3L, 3L);
        fileService.saveUploadedFileInfo(File.createNewFile("pom.xml", "", "xml"), 2L, 2L);
        fileService.saveUploadedFileInfo(File.createNewFile("init.lua", "", "lua"), 2L, 3L);
    }

    @Resource
    FileMapper fileMapper;

    @Test
    void findAllFiles() {
        final List<File> files = fileMapper.findAllFiles();
        files.forEach(System.out::println);
    }
}
