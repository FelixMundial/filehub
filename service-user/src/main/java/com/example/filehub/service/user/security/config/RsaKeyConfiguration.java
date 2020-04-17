package com.example.filehub.service.user.security.config;

import com.example.filehub.commons.service.util.RsaUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

/**
 * @author yinfelix
 * @date 2020/3/9
 */
@Getter
@Setter
@Component
public class RsaKeyConfiguration {

    @Value("${authc.key.public-key-file}")
    private String publicKeyFile;
    @Value("${authc.key.private-key-file}")
    private String privateKeyFile;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void getRsaKeys() {
        this.publicKey = RsaUtils.getPublicKey(publicKeyFile);
        this.privateKey = RsaUtils.getPrivateKey(privateKeyFile);
    }
}
