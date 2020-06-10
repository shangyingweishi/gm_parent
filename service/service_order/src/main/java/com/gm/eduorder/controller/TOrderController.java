package com.gm.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gm.commonutils.JWTUtils;
import com.gm.commonutils.R;
import com.gm.eduorder.entity.TOrder;
import com.gm.eduorder.service.TOrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-04
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
@Api(description = "订单功能")
public class TOrderController {

    @Autowired
    private TOrderService orderService;

    //根据课程id和用户id创建订单信息
    @GetMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){

        String memberId = JWTUtils.getMemberIdByJwtToken(request);

        TOrder order = orderService.createOrder(courseId, memberId);

        return R.ok().data("orderNo", order.getOrderNo());
    }


    //根据订单id查询订单信息
    @GetMapping("/getOrderInfo/{orderNo}")
    public R getOrderInfo(@PathVariable String orderNo){

        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        TOrder order = orderService.getOne(wrapper);

        return R.ok().data("order", order);

    }

    //根据课程id和登录用户判断用户是否付款完成
    @GetMapping("/checkPayStatus/{courseId}/{userId}")
    public Boolean checkPayStatus(@PathVariable String courseId, @PathVariable String userId){

        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", userId);
        wrapper.eq("status", 1);
        int count = orderService.count(wrapper);
        if(count > 0){
            return true;
        }else {
            return false;
        }


    }

}

