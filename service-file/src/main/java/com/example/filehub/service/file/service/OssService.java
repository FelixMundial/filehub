package com.example.filehub.service.file.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.*;
import com.example.filehub.commons.entity.oss.OssCallback;
import com.example.filehub.commons.entity.oss.OssPolicy;
import com.example.filehub.service.file.config.OssConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This demo code show you how to use oss in your application. You can use is directly or
 * refactor is to implement your own logic|architecture.
 */
@Slf4j
@Service
public class OssService {
    @Resource
    private OSS ossClient;

    private final String accessId = OssConfig.accessId;
    private final String endpoint = OssConfig.endpoint;
    private final String bucketName = OssConfig.bucketName;

    private final Long expirationTimeInMinutes = Long.parseLong(OssConfig.expirationTimeInMinutes);
    private final String callbackUrl = OssConfig.callbackUrl;
    private final Long fileMaxSize = Long.parseLong(OssConfig.fileMaxSize) * 1024 * 1024;

    public String getHost() {
        return "https://" + bucketName + "." + endpoint;
    }

    public OssPolicy getPolicy() {
        String filePrefix = OssConfig.filePrefix;
        String host = getHost();
        LocalDateTime now = LocalDateTime.now();
        filePrefix += now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        try {
            java.util.Date expiration = Date.from(now.plusMinutes(expirationTimeInMinutes).atZone(ZoneId.systemDefault()).toInstant());
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, fileMaxSize);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, filePrefix);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            String encodedPolicy = Base64Utils.encodeToString(postPolicy.getBytes(StandardCharsets.UTF_8));
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            OssPolicy ossPolicy = new OssPolicy();
            ossPolicy.setAccessId(accessId);
            ossPolicy.setPolicy(encodedPolicy);
            ossPolicy.setSignature(postSignature);
            ossPolicy.setFilePrefix(filePrefix);
            ossPolicy.setHost(host);
            ossPolicy.setExpiration(expiration.toString());
            ossPolicy.setCallback(callbackUrl);

            return ossPolicy;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @deprecated OSS回调需要使用公网IP
     */
    public OssCallback callback(HttpServletRequest request) {
        OssCallback ossCallback = new OssCallback();
        String filename = request.getParameter("filename");
        filename = "https://".concat(bucketName).concat(".").concat(endpoint).concat("/").concat(filename);

        ossCallback.setFilename(filename);
        ossCallback.setSize(request.getParameter("size"));
        ossCallback.setMimeType(request.getParameter("mimeType"));
        ossCallback.setKey(request.getParameter("key"));
        log.debug(ossCallback.toString());

        return ossCallback;
    }

    public List<OSSObjectSummary> listRemoteFilesViaClient(String bucketName, String prefix) {
        if (!StringUtils.isEmpty(bucketName)) {
            ObjectListing objectListing = ossClient.listObjects(bucketName, prefix);
            return objectListing.getObjectSummaries();
        }
        log.error("bucket名不能为空！");
        return null;
    }

    public String readRemoteFileViaClient(String bucketName, String fileName) throws IOException {
        if (!StringUtils.isEmpty(bucketName)) {
            OSSObject ossObject = ossClient.getObject(bucketName, fileName);
            return IOUtils.readStreamAsString(ossObject.getObjectContent(), CharEncoding.UTF_8);
        }
        log.error("bucket名不能为空！");
        return null;
    }

    public boolean uploadFileWithClient(String bucketName, String fileName) {
        if (!StringUtils.isEmpty(bucketName)) {
            PutObjectResult result = ossClient.putObject(bucketName, fileName,
                    this.getClass().getClassLoader().getResourceAsStream(fileName));
            System.out.println(result.getETag());
        }
        log.error("bucket名不能为空！");
        return false;
    }

//    /**
//     * the file in local machine.
//     */
//    @Value("classpath:/archives/oss-test.json")
//    private Resource localFile;

//    /**
//     * the file in oss remote server.
//     */
//    @Value("oss://" + OssConfig.BUCKET_NAME + "/archives/oss-test.json")
//    private Resource remoteFile;

//    public String readRemoteFileViaResource() throws IOException {
//        return IOUtils.readStreamAsString(remoteFile.getInputStream(),
//                CharEncoding.UTF_8);
//    }

//    public void uploadFileWithOutputStream() throws IOException {
//        try (OutputStream out = ((WritableResource) this.remoteFile).getOutputStream();
//             InputStream in = localFile.getInputStream()) {
//            StreamUtils.copy(in, out);
//        }
//    }
}
