package com.example.filehub.commons.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`file`")
public class File implements Serializable {
    @Id
    @Column(name = "file_id")
//    @GeneratedValue(generator = "JDBC")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @Column(name = "file_real_name")
    private String fileRealName;

    @Column(name = "file_display_name")
    private String fileDisplayName;

    @Column(name = "file_desc")
    private String fileDesc;

    @Column(name = "download_count")
    private Integer downloadCount;

    @Column(name = "file_url")
    private String fileUrl;

    /**
     * is_private
     */
    @Column(name = "privacy_type")
    private Boolean privacyType;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "storage_type")
    private Boolean storageType;

    @Column(name = "sync_type")
    private Boolean syncType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_creation_time")
    private LocalDateTime fileCreationTime;

    @Column(name = "file_creation_uid")
    private Long fileCreationUid;

    @Column(name = "file_last_update_time")
    private LocalDateTime fileLastUpdateTime;

    @Column(name = "file_last_update_uid")
    private Long fileLastUpdateUid;

    private List<Library> parentLibraries;

    private static final long serialVersionUID = 1L;

    public File(String fileRealName, String fileDisplayName, String fileUrl, String fileType, Long fileSize, LocalDateTime fileLastUpdateTime) {
        this.fileRealName = fileRealName;
        this.fileDisplayName = fileDisplayName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.storageType = true;
        this.syncType = true;
        this.fileSize = fileSize;
        this.fileCreationTime = fileLastUpdateTime;
        this.fileLastUpdateTime = fileLastUpdateTime;
    }

    public static File createNewFile(String fileDisplayName, String fileDesc, String fileType) {
        return new File(fileDisplayName, fileDesc, fileType, 1L, LocalDateTime.now());
    }

    public File(String fileDisplayName, String fileDesc, String fileType, Long fileUploaderUid, LocalDateTime uploadTime) {
        this.fileRealName = UUID.randomUUID().toString() + "_" + fileDisplayName.toLowerCase();
        this.fileDisplayName = fileDisplayName;
        this.fileDesc = fileDesc;
        this.downloadCount = new Random().nextInt(10);
        this.fileUrl = "/file/" + fileRealName;
        this.fileType = fileType;
        this.storageType = true;
        this.syncType = true;
        this.fileCreationTime = uploadTime;
        this.fileLastUpdateTime = uploadTime;
        this.fileCreationUid = fileUploaderUid;
        this.fileLastUpdateUid = fileUploaderUid;
    }
}