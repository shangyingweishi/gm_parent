package com.gm.usercenter.service;

import com.gm.usercenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gm.usercenter.entity.vo.RegistVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-02
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void regist(RegistVo registVo);

    UcenterMember geUserByOpenId(String openid);

    Integer registCount(String day);
}
