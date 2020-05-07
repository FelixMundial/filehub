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
public class OssCallbackParam {
    private static final String CALLBACK_BODY = "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}";
    private static final String CALLBACK_BODY_TYPE = "application/x-www-form-urlencoded";
    private String callbackUrl;
    private String callbackBody = CALLBACK_BODY;
    private String callbackBodyType = CALLBACK_BODY_TYPE;

    public OssCallbackParam(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
