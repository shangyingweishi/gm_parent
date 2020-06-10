package com.gm.eduorder.controller;


import com.gm.commonutils.R;
import com.gm.eduorder.service.TOrderService;
import com.gm.eduorder.service.TPayLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-04
 */
@RestController
@RequestMapping("/eduorder/pay")
//@CrossOrigin
@Api(description = "支付功能")
public class TPayLogController {

    @Autowired
    private TPayLogService payLogService;

    //生成二维码
    @GetMapping("/createWxCode/{orderNo}")
    public R CreateWxCode(@PathVariable String orderNo){


        Map map = payLogService.CreateWxCode(orderNo);

        System.out.println(map);
        return R.ok().data(map);
    }

    //检查支付状态
    @GetMapping("/checkPayResult/{orderNo}")
    public R checkPayResult(@PathVariable String orderNo){

        Map<String,String> map = payLogService.checkPayResult(orderNo);
        if(map == null){
            return R.error().message("支付失败");
        }
        if ("SUCCESS".equals(map.get("trade_state"))){
            //修改订单支付状态
            payLogService.updateOrder(map);
            return R.ok().message("支付成功");
        }

        return R.ok().code(25000).message("支付中");
    }



}

