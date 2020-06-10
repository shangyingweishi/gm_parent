package com.gm.oss.controller;

import com.gm.commonutils.R;
import com.gm.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/edu/ossfile")
@CrossOrigin
@Api(description = "oss文件管理")
public class OssController {

    @Autowired
    private OssService ossService;


    @PostMapping
    @ApiOperation(value = "上传头像功能")
    public R uploadOssFile(MultipartFile file) {

        String url = ossService.uploadAvatarFile(file);

        if (!StringUtils.isEmpty(url)) {
            return R.ok().data("url", url);
        } else {
            return R.error();
        }

    }

}
