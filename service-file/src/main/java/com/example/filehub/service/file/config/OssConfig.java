package com.example.filehub.service.file.config;

import com.aliyun.oss.OSS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS common config.
 *
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@Configuration
public class OssConfig {
    public static String accessId;
    public static String endpoint;
    public static String bucketName;
    public static String expirationTimeInMinutes;
    public static String filePrefix;
    public static String callbackUrl;
    public static String fileMaxSize;
    @Autowired
    private OSS ossClient;

    @Value("${spring.cloud.alicloud.access-key}")
    public void setAccessId(String accessId) {
        OssConfig.accessId = accessId;
    }

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    public void setEndpoint(String endpoint) {
        OssConfig.endpoint = endpoint;
    }

    @Value("${spring.cloud.alicloud.oss.bucket-name}")
    public void setBucketName(String bucketName) {
        OssConfig.bucketName = bucketName;
    }

    @Value("${spring.cloud.alicloud.oss.policy.expiration-time-in-minutes}")
    public void setExpirationTimeInMinutes(String expirationTimeInMinutes) {
        OssConfig.expirationTimeInMinutes = expirationTimeInMinutes;
    }

    @Value("${spring.cloud.alicloud.oss.file.prefix}")
    public void setFilePrefix(String filePrefix) {
        OssConfig.filePrefix = filePrefix;
    }

    @Value("${spring.cloud.alicloud.oss.callback-url}")
    public void setCallbackUrl(String callbackUrl) {
        OssConfig.callbackUrl = callbackUrl;
    }

    @Value("${spring.cloud.alicloud.oss.file.max-size}")
    public void setFileMaxSize(String fileMaxSize) {
        OssConfig.fileMaxSize = fileMaxSize;
    }

    /**
     * You can use these code to check or create oss bucket. Or manage buckets in
     * <a href="https://oss.console.aliyun.com/bucket">oss console</a>.
     */
    @Bean
    public ApplicationRunner ossBucketInitRunner() {
        return args -> {
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
            }
        };
    }
}
