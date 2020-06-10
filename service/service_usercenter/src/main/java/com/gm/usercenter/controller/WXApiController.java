package com.gm.usercenter.controller;

import com.gm.commonutils.JWTUtils;
import com.gm.servicebase.entity.MyException;
import com.gm.usercenter.config.WXConfig;
import com.gm.usercenter.entity.UcenterMember;
import com.gm.usercenter.service.UcenterMemberService;
import com.gm.usercenter.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
//@CrossOrigin
public class WXApiController {

    @Autowired
    private UcenterMemberService memberService;

    //返回回调地址
    @GetMapping("/callback")
    public String callBack(String code, String state){

        try {
            //获取地址栏携带的token
            System.out.println(code);
            System.out.println(state);
            //通过token从固定地址得到access_token和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接id secret 和code
            String tokenUrl = String.format(
                    baseAccessTokenUrl,
                    WXConfig.App_Id,
                    WXConfig.App_Secret,
                    code
            );
            //用tokenUrl地址得到access_token和openid
            //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(tokenUrl);
            System.out.println(accessTokenInfo);

            //从accessTokenInfo取出access_token和openid，把accessTokenInfo转换成map集合，根据map的key获取对应的值，使用json转换工具gson
            Gson gson = new Gson();
            HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) map.get("access_token");
            String openid = (String) map.get("openid");

            //把扫码人信息添加到数据库
            //判断数据库是否存在该扫码用户，根据openid
            UcenterMember member = memberService.geUserByOpenId(openid);
            if (member == null){
                //通过access_token和openid请求固定地址获取用户信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );

                String userInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println(userInfo);
                HashMap userMap = gson.fromJson(userInfo, HashMap.class);
                String nickName = (String) userMap.get("nickname");
                String headimgurl = (String) userMap.get("headimgurl");

                //数据库没有该用户信息，则把用户信息存入数据库
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickName);
                member.setAvatar(headimgurl);
                memberService.save(member);

            }
            //用jwt根据user信息生成token字符串
            String jwtToken = JWTUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token=" + jwtToken;

        } catch (Exception e) {
            throw new MyException(20001,"登录失败");
        }


    }

    //生成微信二维码
    @GetMapping("/login")
    public String getWxCode(){

        //固定地址，后面拼接参数
        // 微信开放平台授权baseUrl,%s相当于?占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";


        //对redirect_uri进行URLEncode编码
        String redirect_uri = WXConfig.Redirect_Url;
        try {
            redirect_uri = URLEncoder.encode(redirect_uri, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(
                baseUrl,
                WXConfig.App_Id,
                redirect_uri,
                "gm"
        );

        return "redirect:" + url;

    }

}
