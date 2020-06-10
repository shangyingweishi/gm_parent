package com.gm.eduorder.service;

import com.gm.eduorder.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-04
 */
public interface TPayLogService extends IService<TPayLog> {

    Map CreateWxCode(String orderNo);

    Map checkPayResult(String orderNo);

    void updateOrder(Map<String, String> map);
}
