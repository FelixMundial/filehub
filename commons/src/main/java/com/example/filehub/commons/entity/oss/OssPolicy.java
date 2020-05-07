package com.example.filehub.commons.entity.oss;

import com.example.filehub.commons.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.util.Base64Utils;

import java.io.Serializable;

/**
 * @author yinfelix
 * @date 2020/5/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OssPolicy implements Serializable {
    private String accessId;

    /**
     * 编码后的Policy
     */
    private String policy;

    private String signature;

    /**
     * dir
     */
    private String filePrefix;

    /**
     * 服务器地址
     */
    private String host;

    /**
     * 失效时刻
     */
    private String expiration;

    /**
     * 编码后的回调地址
     */
    private String callback;

    @Setter(value = AccessLevel.PRIVATE)
    @JsonIgnore
    private String callbackUrl;

    public void setCallback(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        OssCallbackParam param = new OssCallbackParam(callbackUrl);
        this.callback = Base64Utils.encodeToString(JsonUtil.getJsonStringFromObjectIgnoresNull(param).getBytes());
    }
}
