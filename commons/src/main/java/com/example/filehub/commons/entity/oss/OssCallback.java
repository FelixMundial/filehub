package com.example.filehub.commons.entity.oss;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yinfelix
 * @date 2020/5/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OssCallback {
    private String filename;

    private String size;

    private String mimeType;

    private String key;
}
