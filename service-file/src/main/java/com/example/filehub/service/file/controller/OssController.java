package com.example.filehub.service.file.controller;

import com.aliyun.oss.model.OSSObjectSummary;
import com.example.filehub.commons.global.dto.BaseResult;
import com.example.filehub.commons.global.dto.factory.BaseResultFactory;
import com.example.filehub.service.file.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 直接通过oss api查询云端文件列表
     *
     * @param bucketName
     * @return
     */
    @GetMapping("/{bucketName}")
    public BaseResult listRemoteFiles(@PathVariable("bucketName") String bucketName) {
        List<OSSObjectSummary> summaries = ossService.listRemoteFilesViaClient(bucketName, "archives");
        if (!summaries.isEmpty()) {
            return BaseResultFactory.getSuccessResult(summaries);
        }
        return null;
    }
}
