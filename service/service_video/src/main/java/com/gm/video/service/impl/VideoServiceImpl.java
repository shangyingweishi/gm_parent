package com.gm.video.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.gm.servicebase.entity.MyException;
import com.gm.video.config.VideoUploadConfig;
import com.gm.video.service.VideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Override
    public String uploadVideo(MultipartFile file) {

        InputStream is = null;
        try {
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            is = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(VideoUploadConfig.Key_Id, VideoUploadConfig.Key_Secret, title, fileName, is);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
            return response.getVideoId();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String delVideo(String videoSourceId) {

        try {
            DefaultAcsClient client = initVodClient(VideoUploadConfig.Key_Id, VideoUploadConfig.Key_Secret);

            DeleteVideoRequest request = new DeleteVideoRequest();

            request.setVideoIds(videoSourceId);

            DeleteVideoResponse response = client.getAcsResponse(request);

            return response.getRequestId();
        } catch (ClientException e) {
            throw new MyException(20001, "视频删除失败");

        }

    }

    @Override
    public void delBatch(List<String> videoIdList) {

        try {
            DefaultAcsClient client = initVodClient(VideoUploadConfig.Key_Id, VideoUploadConfig.Key_Secret);

            DeleteVideoRequest request = new DeleteVideoRequest();

            String ids = StringUtils.join(videoIdList.toArray(), ",");

            request.setVideoIds(ids);

            DeleteVideoResponse response = client.getAcsResponse(request);


        } catch (ClientException e) {
            throw new MyException(20001, "视频删除失败");

        }

    }

    @Override
    public String getVideoAuth(String id) {

        try {
            DefaultAcsClient client = initVodClient(VideoUploadConfig.Key_Id, VideoUploadConfig.Key_Secret);

            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            request.setVideoId(id);

            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            String playAuth = response.getPlayAuth();

            return playAuth;

        } catch (ClientException e) {
            throw new MyException(20001, "获取视频凭证失败");
        }


    }


    private  DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
