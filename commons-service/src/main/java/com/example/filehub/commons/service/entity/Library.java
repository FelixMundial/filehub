package com.example.filehub.commons.service.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文档库
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "library")
public class Library implements Serializable {
    @Id
    @Column(name = "library_id")
//    @GeneratedValue(generator = "JDBC")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer libraryId;

    /**
     * 可重复
     */
    @Column(name = "library_name")
    private String libraryName;

    @Column(name = "library_desc")
    private String libraryDesc;

    @Column(name = "owner_uid")
    private Long ownerUid;

    @Column(name = "followers_count")
    private Integer followersCount;

    @Column(name = "library_url")
    private String libraryUrl;

    /**
     * is_private
     */
    @Column(name = "privacy_type")
    private Boolean privacyType;

    @Column(name = "library_creation_time")
    private LocalDateTime libraryCreationTime;

    @Column(name = "library_creation_uid")
    private Long libraryCreationUid;

    @Column(name = "library_last_update_time")
    private LocalDateTime libraryLastUpdateTime;

    @Column(name = "library_last_update_uid")
    private Long libraryLastUpdateUid;

    private List<File> files;
    private List<UserAccountInfo> collaborators;

    private static final long serialVersionUID = 1L;

    public Library(String libraryName, String libraryDesc, Long ownerUid, LocalDateTime creationTime) {
        this.libraryName = libraryName;
        this.libraryDesc = libraryDesc;
        this.ownerUid = ownerUid;
        this.libraryUrl = "/" + ownerUid + "/" + libraryName;
        this.followersCount = new Random().nextInt(100);
        this.libraryCreationTime = creationTime;
        this.libraryLastUpdateTime = creationTime;
        this.libraryCreationUid = ownerUid;
        this.libraryLastUpdateUid = ownerUid;
    }
}