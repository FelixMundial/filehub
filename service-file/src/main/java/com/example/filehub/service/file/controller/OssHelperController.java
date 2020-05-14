package com.example.filehub.service.file.controller;

import com.example.filehub.commons.entity.oss.OssCallback;
import com.example.filehub.commons.entity.oss.OssPolicy;
import com.example.filehub.commons.global.dto.BaseResult;
import com.example.filehub.commons.global.dto.factory.BaseResultFactory;
import com.example.filehub.service.file.service.OssService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yinfelix
 * @apiNote Web前端直接与OSS交互以处理上传下载请求，而不上传至后端微服务中；
 * 因Access Key信息存在后端，故后端只接受前端的/getPolicy授权请求
 * @date 2020/5/6
 */
@RestController
@RequestMapping("/v1/oss")
public class OssHelperController {
    @Autowired
    private OssService ossService;

    @ApiOperation("获取oss临时授权签名")
    @GetMapping("/getPolicy")
    public BaseResult getPolicy() {
        OssPolicy ossPolicy = ossService.getPolicy();
        if (ossPolicy != null && ossPolicy.getHost().equals(ossService.getHost())) {
            return BaseResultFactory.getSuccessResultWithData(ossPolicy);
        }
        return BaseResultFactory.getFailureResult(HttpStatus.SERVICE_UNAVAILABLE.value(), "服务暂不可用！");
    }

    //    @ApiOperation("oss上传成功回调")
//    @PostMapping("/callback")
    public void callback(HttpServletRequest request) {
        System.out.println("oss上传成功回调");
        OssCallback ossCallback = ossService.callback(request);
    }
}
