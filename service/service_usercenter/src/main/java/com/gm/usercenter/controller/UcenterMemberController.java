package com.gm.usercenter.controller;


import com.gm.commonutils.JWTUtils;
import com.gm.commonutils.R;
import com.gm.usercenter.entity.UcenterMember;
import com.gm.usercenter.entity.vo.RegistVo;
import com.gm.usercenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-02
 */
@RestController
@RequestMapping("/ucenter/user")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    //登录
    @PostMapping("/login")
    public R login(@RequestBody(required = false) UcenterMember ucenterMember){

        String token = memberService.login(ucenterMember);

        return R.ok().data("token", token);

    }

    //注册
    @PostMapping("/regist")
    public R regist(@RequestBody RegistVo registVo){

        memberService.regist(registVo);

        return R.ok();
    }

    //通过token获取用户信息
    @GetMapping("/getUserInfo")
    public R getUserInfo(HttpServletRequest request){

        String memberIdByJwtToken = JWTUtils.getMemberIdByJwtToken(request);
        UcenterMember user = memberService.getById(memberIdByJwtToken);

        return R.ok().data("user",user);
    }

    //根据用户id获取用户信息供order服务调用
    @GetMapping("/getUserInfo/{id}")
    public UcenterMember getUserInfo(@PathVariable String id){

        UcenterMember ucenterMember = memberService.getById(id);

        return ucenterMember;

    }

    //根据条件查询网站的注册人数
    @GetMapping("/registCount/{day}")
    public Integer registCount(@PathVariable String day){

        Integer count = memberService.registCount(day);

        return count;

    }

}

