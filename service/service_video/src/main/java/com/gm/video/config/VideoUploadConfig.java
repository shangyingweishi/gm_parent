package com.gm.video.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VideoUploadConfig implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String keyId;

    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;

    public static String Key_Id;
    public static String Key_Secret;

    @Override
    public void afterPropertiesSet() throws Exception {

        Key_Id = keyId;
        Key_Secret = keySecret;

    }
}
