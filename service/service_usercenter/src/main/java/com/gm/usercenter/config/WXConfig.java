package com.gm.usercenter.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WXConfig implements InitializingBean {

    @Value("${wx.open.app_id}")
    private String appId;

    @Value("${wx.open.app_secret}")
    private String appSecret;

    @Value("${wx.open.redirect_url}")
    private String redirectUrl;

    public static String App_Id;
    public static String App_Secret;
    public static String Redirect_Url;

    @Override
    public void afterPropertiesSet() throws Exception {
        App_Id = appId;
        App_Secret = appSecret;
        Redirect_Url = redirectUrl;
    }
}
