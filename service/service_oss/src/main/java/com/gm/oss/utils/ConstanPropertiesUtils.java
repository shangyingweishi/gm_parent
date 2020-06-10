package com.gm.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstanPropertiesUtils implements InitializingBean {

    //读取配置文件内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endPoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    //创建公开静态常量
    public static String End_Point;
    public static String Key_Id;
    public static String Key_Secret;
    public static String Bucket_Name;

    @Override
    public void afterPropertiesSet() throws Exception {
        End_Point = endPoint;
        Key_Id = keyId;
        Key_Secret = keySecret;
        Bucket_Name = bucketName;
    }
}
