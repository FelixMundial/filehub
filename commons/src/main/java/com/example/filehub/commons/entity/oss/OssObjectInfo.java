package com.example.filehub.commons.entity.oss;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author yinfelix
 * @date 2020/5/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OssObjectInfo {
    private String fileName;

    private String fileUrl;

    private String fileSize;

    private String fileEtag;

    private String fileType;

    private String fileStatus;

    private Date fileLastModifiedDate;
}
