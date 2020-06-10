package com.gm.eduorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.gm.eduorder.client.CourseClient;
import com.gm.eduorder.client.UserClient;
import com.gm.eduorder.entity.TOrder;
import com.gm.eduorder.entity.vo.CourseWebInfoVo;
import com.gm.eduorder.entity.vo.UcenterMember;
import com.gm.eduorder.mapper.TOrderMapper;
import com.gm.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gm.eduorder.utils.HttpClient;
import com.gm.eduorder.utils.OrderNumUtils;
import com.gm.servicebase.entity.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-04
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UserClient userClient;

    @Override
    public TOrder createOrder(String courseId, String memberId) {
        CourseWebInfoVo courseInfo = courseClient.getCourseInfo(courseId);
        UcenterMember userInfo = userClient.getUserInfo(memberId);

        TOrder order = new TOrder();
        order.setOrderNo(OrderNumUtils.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName(courseInfo.getTeacherName());
        order.setMemberId(userInfo.getId());
        order.setNickname(userInfo.getNickname());
        order.setMobile(userInfo.getMobile());
        order.setTotalFee(courseInfo.getPrice());
        order.setPayType(1);
        order.setStatus(0);

        baseMapper.insert(order);

        return order;

    }



}
