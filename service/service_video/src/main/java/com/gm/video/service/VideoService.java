package com.gm.video.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    String uploadVideo(MultipartFile file);

    String delVideo(String videoSourceId);


    void delBatch(List<String> videoIdList);

    String getVideoAuth(String id);
}
