package com.example.filehub.service.file.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.example.filehub.service.file.config.OssConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class OssServiceTest {
    @Autowired
    private OssService ossService;

    @Value("classpath:/archives/oss-test.json")
    private Resource localFile;

    @javax.annotation.Resource
    private OSS ossClient;

    @Test
    void testOssClient() {
        ObjectListing objectListing = ossClient.listObjects(OssConfig.bucketName, "oss");
        List<OSSObjectSummary> summaries = objectListing.getObjectSummaries();
        summaries.forEach(summary -> {
            System.out.println(summary.getKey());
        });
    }

    @Test
    void readRemoteFileViaClient() throws IOException {
        String fileContent = ossService.readRemoteFileViaClient(OssConfig.bucketName, ((ClassPathResource) localFile).getPath());
        System.out.println(fileContent);
    }

    @Test
    void uploadFileWithClient() {
        boolean uploadFlag = ossService.uploadFileWithClient(OssConfig.bucketName, ((ClassPathResource) localFile).getPath());
        System.out.println(uploadFlag);
    }
}