package com.gm.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.gm.oss.service.OssService;
import com.gm.oss.utils.ConstanPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadAvatarFile(MultipartFile file) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstanPropertiesUtils.End_Point;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstanPropertiesUtils.Key_Id;
        String accessKeySecret = ConstanPropertiesUtils.Key_Secret;
        String bucketName = ConstanPropertiesUtils.Bucket_Name;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = null;
        try {
            //文件分类管理,防止文件同名覆盖
            String filename = file.getOriginalFilename();
            filename = UUID.randomUUID().toString().replaceAll("-", "") + filename;
            String time = new DateTime().toString("yyyy/MM/dd");
            filename = time + "/" + filename;

            inputStream = file.getInputStream();
            ossClient.putObject(bucketName, filename, inputStream);

            String url = "https://" + bucketName + "." + endpoint + "/" + filename;

            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }
}
