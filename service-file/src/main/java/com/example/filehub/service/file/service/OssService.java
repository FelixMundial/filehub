package com.example.filehub.service.file.service;

import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.*;
import com.example.filehub.commons.entity.oss.OssCallback;
import com.example.filehub.commons.entity.oss.OssPolicy;
import com.example.filehub.service.file.config.OssConfig;
import org.apache.commons.codec.CharEncoding;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author yinfelix
 * @date 2020/5/8
 */
public interface OssService {
    String getHost();

    OssPolicy getPolicy();

    OssCallback callback(HttpServletRequest request);

    List<OSSObjectSummary> listRemoteFilesViaClient(String bucketName, String prefix);

    /*
    获取云端文件内容
     */
    String readRemoteFileViaClient(String bucketName, String fileName) throws IOException;

    boolean uploadFileWithClient(String bucketName, String fileName);
}
