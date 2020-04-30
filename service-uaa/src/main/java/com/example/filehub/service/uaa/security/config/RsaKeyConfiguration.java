package com.example.filehub.service.uaa.security.config;

//import com.example.filehub.commons.service.util.RsaUtils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author yinfelix
 * @date 2020/3/9
 * @deprecated
 */
//@RefreshScope
@Getter
@Setter
//@Component
public class RsaKeyConfiguration {
    private PublicKey publicKey;
    private PrivateKey privateKey;

//    @Value("${rsa.key.pub}")
//    private String publicKeyFile;
//    @Value("${rsa.key}")
//    private String privateKeyFile;
//
//    @PostConstruct
//    public void getRsaKeys() {
//        this.publicKey = RsaUtils.getPublicKeyFromFile(publicKeyFile);
//        this.privateKey = RsaUtils.getPrivateKeyFromFile(privateKeyFile);
//    }
}
