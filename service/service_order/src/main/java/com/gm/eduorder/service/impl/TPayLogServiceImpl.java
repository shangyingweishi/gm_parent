package com.gm.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.gm.eduorder.entity.TOrder;
import com.gm.eduorder.entity.TPayLog;
import com.gm.eduorder.mapper.TPayLogMapper;
import com.gm.eduorder.service.TOrderService;
import com.gm.eduorder.service.TPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gm.eduorder.utils.HttpClient;
import com.gm.servicebase.entity.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-04
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    @Autowired
    private TOrderService orderService;

    @Override
    public Map CreateWxCode(String orderNo) {
        try {
            QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            TOrder order = orderService.getOne(wrapper);

            Map<String, String> map = new HashMap<>();
            //1、设置支付参数
            map.put("appid", "wx74862e0dfcf69954");
            map.put("mch_id", "1558950191");
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body", order.getCourseTitle());
            map.put("out_trade_no", orderNo);
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            map.put("trade_type", "NATIVE");

            //2、HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置参数
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //返回参数
            String xmlParam = client.getContent();
            Map<String, String> xmlToMap = WXPayUtil.xmlToMap(xmlParam);

            //封装数据
            Map returnMap = new HashMap();
            returnMap.put("out_trade_no", orderNo);
            returnMap.put("course_id", order.getCourseId());
            returnMap.put("total_fee", order.getTotalFee());
            returnMap.put("result_code", xmlToMap.get("result_code"));//返回二维码操作的状态码
            returnMap.put("code_url", xmlToMap.get("code_url"));//二维码地址

            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001,"获取支付二维码失败");
        }
    }

    @Override
    public Map checkPayResult(String orderNo) {

        try {
            //封装数据
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //发送请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //返回数据
            String xml = client.getContent();
            Map<String, String> map = WXPayUtil.xmlToMap(xml);
            return map;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public void updateOrder(Map<String,String> map) {
        String tradeNo = map.get("out_trade_no");
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", tradeNo);
        TOrder order = orderService.getOne(wrapper);
        if(order.getStatus() == 1){return;}

        order.setStatus(1);
        orderService.updateById(order);

        TPayLog payLog = new TPayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);//插入到支付日志表


    }

}
