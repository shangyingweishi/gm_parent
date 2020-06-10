package com.gm.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gm.commonutils.JWTUtils;
import com.gm.commonutils.MD5;
import com.gm.servicebase.entity.MyException;
import com.gm.usercenter.entity.UcenterMember;
import com.gm.usercenter.entity.vo.RegistVo;
import com.gm.usercenter.mapper.UcenterMemberMapper;
import com.gm.usercenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-02
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(UcenterMember ucenterMember) {

        String mobile = ucenterMember.getMobile();

        String password = ucenterMember.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) ){
            throw new MyException(20001,"登陆失败");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember member = baseMapper.selectOne(wrapper);

        if (member == null){
            throw new MyException(20001, "该手机号无法登录");
        }

        //数据库存的时加密后的密码，所以页面传来的密码需要加密再比较,MD5加密工具类
        if (!MD5.encrypt(password).equals(member.getPassword())){
            throw new MyException(20001, "密码错误，登录失败");
        }

        //判断账号是否禁用
        if (member.getIsDisabled()){
            throw new MyException(20001, "该账号无法登录");
        }

        String token = JWTUtils.getJwtToken(member.getId(), member.getNickname());

        return token;
    }

    @Override
    public void regist(RegistVo registVo) {

        UcenterMember ucenterMember = new UcenterMember();

        String code = registVo.getCode();
        String mobile = registVo.getMobile();
        String nickname = registVo.getNickname();
        String password = registVo.getPassword();
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)){
            throw new MyException(20001, "信息不能为空，注册失败");
        }

        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            throw new MyException(20001, "验证码错误，注册失败");
        }

        //判断手机号是否已经存在
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer integer = baseMapper.selectCount(wrapper);
        if (integer > 0){
            throw new MyException(20001, "该手机号已注册");
        }

        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setNickname(nickname);
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("https://online-edu-gm.oss-cn-shanghai.aliyuncs.com/2020/05/27/7bea7753786e453291842999b35a7abff0Qhns-ig5yZ1aT3cSiu-qm.jpg");

        baseMapper.insert(ucenterMember);

    }

    @Override
    public UcenterMember geUserByOpenId(String openid) {

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);

        return ucenterMember;
    }

    @Override
    public Integer registCount(String day) {

        Integer count = baseMapper.getRegistCount(day);

        return count;
    }
}
