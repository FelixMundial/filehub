package com.example.filehub.service.file.controller;

import com.aliyun.oss.model.OSSObjectSummary;
import com.example.filehub.commons.global.dto.BaseResult;
import com.example.filehub.commons.global.dto.factory.BaseResultFactory;
import com.example.filehub.service.file.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author yinfelix
 * @date 2020/5/7
 */
@RestController
//@RequestMapping("/file")
public class OssController {
    @Autowired
    private OssService ossService;

    @GetMapping("/{bucketName}/**")
    public BaseResult readRemoteFiles(@PathVariable("bucketName") String bucketName, HttpServletRequest request) throws IOException {
        String fullPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String fileKey = fullPath.substring(fullPath.indexOf(bucketName) + bucketName.length() + 1, fullPath.length());
        final String fileContent = ossService.readRemoteFileViaClient(bucketName, fileKey);
        return BaseResultFactory.getSuccessResultWithData(fileContent);
    }

    /**
     * @param bucketName
     * @return
     * @deprecated 直接通过oss api查询云端文件列表
     */
    @GetMapping("/{bucketName}")
    public BaseResult listRemoteFiles(@PathVariable("bucketName") String bucketName) {
        List<OSSObjectSummary> summaries = ossService.listRemoteFilesViaClient(bucketName, "archives");
        if (!summaries.isEmpty()) {
            return BaseResultFactory.getSuccessResultWithData(summaries);
        }
        return null;
    }
}
