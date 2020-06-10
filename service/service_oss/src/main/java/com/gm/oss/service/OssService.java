package com.gm.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface OssService {

    //上传文件到oss
    String uploadAvatarFile(MultipartFile file);
}
