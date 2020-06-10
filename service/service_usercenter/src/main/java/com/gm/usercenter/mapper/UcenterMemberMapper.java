package com.gm.usercenter.mapper;

import com.gm.usercenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-06-02
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer getRegistCount(String day);
}
