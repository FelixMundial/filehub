package com.example.filehub.commons.entity.oss;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author yinfelix
 * @date 2020/5/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OssObject {
    private String fileName;

    private String fileUrl;

    private String fileSize;

    private String fileEtag;

    private String fileType;

    private String fileStatus;

    private Date fileLastModifiedDate;

    private List<Long> fileParentLibraryIds;
}
